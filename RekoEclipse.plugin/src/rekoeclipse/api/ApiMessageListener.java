/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Semaphore;
import java.util.zip.DataFormatException;

/**
 *
 * @author sm
 */
public class ApiMessageListener implements Listener {

	private final class RequestState {

		private final ApiMessageHeader header;
		private byte[] reply;
		private final Semaphore event = new Semaphore(1);

		public RequestState(ApiMessageHeader request) {
			this.header = request;
		}

		public Semaphore getSemaphore() {
			return event;
		}

		void setReply(byte[] data) {
			this.reply = data;
		}

		public byte[] getReply() {
			return this.reply;
		}
	}

	private final HashMap<Integer, RequestState> messages = new HashMap<>();

	private ByteArrayOutputStream inData = new ByteArrayOutputStream();

	@Override
	public void onOpen(WebSocket webSocket) {
		Listener.super.onOpen(webSocket);
	}

	public void registerRequest(ApiMessageHeader requestHeader) {
		messages.put(requestHeader.getSequenceNumber(), new RequestState(requestHeader));
	}

	private void dispatchEvent(byte[] eventMessage){
		
	}
	
	@Override
	public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
		try {
			inData.write(data.array());
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}

		if (last) {
			byte[] msgData = inData.toByteArray();
			ApiMessageHeader header = ApiMessageHeader.deserialize(msgData);

			ApiRequestType msgType = header.getMessageType();
			switch (msgType) {
				case REQUEST:
				case EVENT:
					break;
				default:
					return CompletableFuture.failedStage(new DataFormatException(
							String.format("Got unexpected message type '%d'", msgType.getValue())
					));
			}

			if (msgType == ApiRequestType.EVENT) {
				dispatchEvent(msgData);
			} else {
				int seqNo = header.getSequenceNumber();
				if (!messages.containsKey(seqNo)) {
					return CompletableFuture.failedStage(new DataFormatException(
							String.format("Got unexpected reply '%d'", seqNo)
					));
				}

				RequestState state = messages.get(seqNo);
				state.setReply(msgData);
				state.getSemaphore().notify();

				inData = new ByteArrayOutputStream();
			}
		}
		return Listener.super.onBinary(webSocket, data, last);
	}
	
	public CompletableFuture<byte[]> waitForMessage(int sequenceNumber){
		return FutureExtensions.supplyAsync(() -> {
			RequestState state = messages.get(sequenceNumber);
			state.getSemaphore().wait();
			
			byte[] reply = state.getReply();
			return reply;
		});
	}
}
