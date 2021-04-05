package com.smx.rekoeclipse.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator extends AbstractUIPlugin {	
	/*private void createRekoClient(BundleContext context){
		String rekoPath = context.getProperty("reko.path");
		
		RekoHostService rekoHost = new RekoHostService(rekoPath);
		
		context.registerService(RekoHostService.class, rekoHost, null);
		
		ApiClient client = null;
		try {
			client = new ApiClient("ws://127.0.0.1/" + rekoHost.getSocketPort());
		} catch (URISyntaxException ex) {
			throw new Error(ex);
		}
		
		context.registerService(ApiClient.class, client, null);
	}*/
	
	private static BundleContext context;

	public static BundleContext getBundleContext() {
		return context;
	}
	
	public static <T> T getService(Class<T> clazz){
		if(context == null)
			return null;
		
		ServiceReference<T> ref = context.getServiceReference(clazz);
		return context.getService(ref);
	}
	
	@Override
	public void start(BundleContext context){
		this.context = context;
		
		//createRekoClient(context);
	}

}
