/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.api.protocol;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.DataFormatException;
import javax.inject.Inject;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.services.log.Logger;
//import org.eclipse.e4.ui.di.UISynchronize;
import rekoeclipse.support.ManualResetEvent;

/**
 *
 * @author sm
 */
public class ApiMessageListener implements Listener {

	private final Executor waitQueue = Executors.newCachedThreadPool();

	@Inject
	private Logger logger;
	
	//@Inject
	//private UISynchronize sync;

	private final class RequestState {

		private final ApiMessageHeader header;
		private byte[] reply;

		private final ManualResetEvent event = new ManualResetEvent();

		public RequestState(ApiMessageHeader request) {
			this.header = request;
		}

		public ManualResetEvent getSemaphore() {
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

	@Override
	public void onOpen(WebSocket webSocket) {
		Listener.super.onOpen(webSocket);
	}

	public void registerRequest(ApiMessageHeader requestHeader) {
		messages.put(requestHeader.getSequenceNumber(), new RequestState(requestHeader));
	}

	private void dispatchEvent(byte[] eventMessage) {

	}

	@Override
	public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {	
		if (!last) {
			throw new UnsupportedOperationException("Chunked messages not supported");
		}
		
		// C# defaults to Little Endian
		data.order(ByteOrder.LITTLE_ENDIAN);

		ApiMessageHeader header;

		{
			byte[] headerData = new byte[ApiMessageHeader.SIZE];
			data.get(headerData);
			header = ApiMessageHeader.deserialize(headerData);
		}
		
		//TODO: validate message size?
		byte[] messageBody = new byte[header.getDataLength()];
		data.get(messageBody);

		ApiMessageType msgType = header.getMessageType();
		switch (msgType) {
			case REQUEST:
			case EVENT:
				break;
			default:
				return CompletableFuture.failedStage(new DataFormatException(
						String.format("Got unexpected message type '%d'", msgType.getValue())
				));
		}

		if (msgType == ApiMessageType.EVENT) {
			dispatchEvent(messageBody);
		} else {
			int replySeqNo = header.getSequenceNumber();
			int requestSeqNo = replySeqNo - 1;
		
			// check for seqNo - 1 (saved request ID)
			if (!messages.containsKey(requestSeqNo)) {
				return CompletableFuture.failedStage(new DataFormatException(
						String.format("Got unexpected reply '%d'", replySeqNo)
				));
			}

			RequestState state = messages.get(requestSeqNo);
			state.setReply(messageBody);

			state.getSemaphore().set();
		}

		return Listener.super.onBinary(webSocket, data, last);
	}

	public CompletableFuture<byte[]> waitForMessage(int sequenceNumber) {
		return FutureExtensions.supplyAsync(() -> {
			RequestState state = messages.get(sequenceNumber);

			state.getSemaphore().waitOne();

			byte[] reply = state.getReply();
			return reply;
		});
	}
}
