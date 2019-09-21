package rekoeclipse.api.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ApiRequestMessage {
	private ApiMessageHeader header;
	private byte[] body;

	public ApiMessageHeader getHeader() {
		return header;
	}
	
	public ApiRequestMessage(ApiMessageHeader header, byte[] data) {
		this.header = header;
		this.body = data;
	}
	
	public byte[] serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(ApiMessageHeader.SIZE + body.length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		// write header
		buffer.put(header.getMessageType().getValue());
		buffer.putInt(header.getDataLength());
		buffer.putInt(header.getSequenceNumber());
		// write body
		buffer.put(body);
		
		return buffer.array();
	}
}
