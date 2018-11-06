package rekoeclipse;

import java.nio.ByteBuffer;

public class ApiRequestMessage {
	private ApiMessageHeader header;
	private byte[] body;
	
	public ApiRequestMessage(ApiMessageHeader header, byte[] data) {
		this.header = header;
		this.body = data;
	}
	
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(ApiMessageHeader.SIZE + body.length);
		// write header
		buffer.put(header.getMessageType().getValue());
		buffer.putInt(header.getDataLength());
		buffer.putInt(header.getSequenceNumber());
		// write body
		buffer.put(body);
		
		return buffer.array();
	}
}
