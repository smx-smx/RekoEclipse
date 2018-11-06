package rekoeclipse;

public enum ApiRequestType {
	REQUEST((byte)0);
	
	private final byte value;
	private ApiRequestType(byte value) {
		this.value = value;
	}
	public byte getValue() {
		return value;
	}
}
