package com.smx.rekoeclipse.api.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ApiMessageHeader {
	private ApiMessageType messageType;
	private int dataLength;
	private int sequenceNumber;
	
	public static final int SIZE = (4 * 2) + 1; 
	
	private ApiMessageHeader(ApiMessageType type, int dataLength, int sequenceNumber) {
		setMessageType(type);
		setDataLength(dataLength);
		setSequenceNumber(sequenceNumber);
	}
	
	public static ApiMessageHeader newRestRequest(int dataLength, int sequenceNumber) {
		return new ApiMessageHeader(ApiMessageType.REQUEST, dataLength, sequenceNumber);
	}
	
	public ApiMessageType getMessageType() {
		return messageType;
	}
	private void setMessageType(ApiMessageType messageType) {
		this.messageType = messageType;
	}
	public int getDataLength() {
		return dataLength;
	}
	private void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	private void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	public static ApiMessageHeader deserialize(byte[] data){
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		
		byte msgType = buf.get();
		int msgSize = buf.getInt();
		int seqNum = buf.getInt();
		
		return new ApiMessageHeader(
				ApiMessageType.fromValue(msgType),
				msgSize,
				seqNum
		);
	}
}
