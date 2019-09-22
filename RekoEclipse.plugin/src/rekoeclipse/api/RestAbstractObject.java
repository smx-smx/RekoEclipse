/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.api;

import javax.inject.Inject;
import org.eclipse.e4.core.di.extensions.Service;
//import rekoeclipse.services.ApiClient;

/**
 *
 * @author sm
 */
public abstract class RestAbstractObject<T> {
	//@Inject
	//@Service
	//private ApiClient client;
	
	private final String objectUrl;
	
	public RestAbstractObject(String urlPath){
		this.objectUrl = urlPath;
	}
	
	protected void get(){
		
	}
	
}
