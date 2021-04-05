package com.smx.rekoeclipse.api.protocol;

public enum ApiMessageType {
	REQUEST((byte)0),
	EVENT((byte)1);
	
	private final byte value;
	private ApiMessageType(byte value) {
		this.value = value;
	}
	public byte getValue() {
		return value;
	}
	
	public static ApiMessageType fromValue(byte value){
		return values()[value];
	}
}
