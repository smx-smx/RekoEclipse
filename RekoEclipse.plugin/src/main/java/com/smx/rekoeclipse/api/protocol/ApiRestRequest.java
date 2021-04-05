package com.smx.rekoeclipse.api.protocol;

import java.util.Map;

public class ApiRestRequest {
	private String method;
	private String url;
	
	private Map<String, Object> data;
	
	public ApiRestRequest(String method, String url, Map<String, Object> data) {
		setMethod(method);
		setUrl(url);
		setData(data);
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
