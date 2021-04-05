 
package rekoeclipse.handlers;

import java.util.HashMap;
import javax.inject.Inject;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Service;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.smx.rekoeclipse.api.protocol.HttpMethod;
//import com.smx.rekoeclipse.services.ApiClient;

public class OpenCommand {
	
	//@Inject
	//@Service
	//private ApiClient client;
	
	@Execute
	public void execute(Shell shell) {	
		FileDialog dialog = new FileDialog(shell);
		dialog.open();
		
		String fileName = dialog.getFileName();
		
		/*client.sendRestRequest(HttpMethod.POST, "/projects", new HashMap<String, Object>(){{
			put("request", "load");
			put("filename", fileName);
			put("loader", null);
		}}).thenAccept(reply -> {
			System.out.println("REPLY");
		});*/
		
		System.out.println("load request sent");
	}
}