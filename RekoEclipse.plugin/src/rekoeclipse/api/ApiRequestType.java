package rekoeclipse.api;

public enum ApiRequestType {
	REQUEST((byte)0),
	EVENT((byte)1);
	
	private final byte value;
	private ApiRequestType(byte value) {
		this.value = value;
	}
	public byte getValue() {
		return value;
	}
	
	public static ApiRequestType fromValue(byte value){
		return values()[value];
	}
}
