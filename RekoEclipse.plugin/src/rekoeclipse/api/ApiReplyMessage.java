/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.api;

import java.util.Map;

/**
 *
 * @author sm
 */
public class ApiReplyMessage {
	private int seqno;
	private int errcode;
	private String errstr;
	private Map<String, Object> data;
}
