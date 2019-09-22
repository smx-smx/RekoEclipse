/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.services;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import io.reactivex.Completable;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import rekoeclipse.plugin.OsgiServiceBase;
/**
 *
 * @author sm
 */
@Component(service = ApiClient.class)
public class ApiClient extends OsgiServiceBase implements AutoCloseable {

	@Override
	public void close() throws Exception {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private HubConnection connection;
	
	public ApiClient(){
	}
	
	@Activate
	protected void activate(ComponentContext ctx){
		this.connection = HubConnectionBuilder
				.create("http://127.0.0.1:5004/reko/main")
				.shouldSkipNegotiate(true)
				.build();
		
		this.connection.start();
	}
}
