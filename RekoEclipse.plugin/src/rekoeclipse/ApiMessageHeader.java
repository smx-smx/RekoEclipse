package rekoeclipse;

public class ApiMessageHeader {
	private ApiRequestType messageType;
	private int dataLength;
	private int sequenceNumber;
	
	public static final int SIZE = (4 * 2) + 1; 
	
	private ApiMessageHeader(ApiRequestType type, int dataLength, int sequenceNumber) {
		setMessageType(type);
		setDataLength(dataLength);
		setSequenceNumber(sequenceNumber);
	}
	
	public static ApiMessageHeader newRestRequest(int dataLength, int sequenceNumber) {
		return new ApiMessageHeader(ApiRequestType.REQUEST, dataLength, sequenceNumber);
	}
	
	public ApiRequestType getMessageType() {
		return messageType;
	}
	private void setMessageType(ApiRequestType messageType) {
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
	
	
}