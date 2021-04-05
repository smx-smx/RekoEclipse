package rekoeclipse.handlers

import org.eclipse.e4.core.di.annotations.Execute
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.swt.widgets.Shell

//import rekoeclipse.services.ApiClient;
class OpenCommand {
    //@Inject
    //@Service
    //private ApiClient client;
    @Execute
    fun execute(shell: Shell?) {
        val dialog = FileDialog(shell)
        dialog.open()
        val fileName = dialog.fileName

        /*client.sendRestRequest(HttpMethod.POST, "/projects", new HashMap<String, Object>(){{
			put("request", "load");
			put("filename", fileName);
			put("loader", null);
		}}).thenAccept(reply -> {
			System.out.println("REPLY");
		});*/println("load request sent")
    }
}