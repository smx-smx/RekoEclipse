 
package rekoeclipse.handlers;

import java.util.HashMap;
import javax.inject.Inject;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import rekoeclipse.api.HttpMethod;
import rekoeclipse.services.ApiClient;

@Component
public class OpenCommand {
	@Reference
	private ApiClient client;
	
	@Execute
	public void execute(Shell shell) {	
		FileDialog dialog = new FileDialog(shell);
		dialog.open();
		
		String fileName = dialog.getFileName();
		
		client.sendRestRequest(HttpMethod.POST, "/projects", new HashMap<String, Object>(){{
			put("request", "load");
			put("filename", fileName);
			put("loader", null);
		}});
	}
}