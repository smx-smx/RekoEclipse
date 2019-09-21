/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.api.protocol;

import java.util.Map;

/**
 *
 * @author sm
 */
public class ApiRestReply {
	private int statusCode;
	private String errorText;
	private Map<String, Object> data;

	public int getStatusCode() {
		return statusCode;
	}

	public String getErrorString() {
		return errorText;
	}

	public Map<String, Object> getData() {
		return data;
	}
}
