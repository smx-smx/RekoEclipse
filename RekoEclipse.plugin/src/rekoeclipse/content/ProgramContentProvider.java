package rekoeclipse.content;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.extensions.Service;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import rekoeclipse.api.protocol.ApiRestReply;
import rekoeclipse.api.protocol.HttpMethod;
import rekoeclipse.plugin.Activator;
import rekoeclipse.plugin.PluginContext;

import rekoeclipse.services.ApiClient;

public class ProgramContentProvider implements ITreeContentProvider {

	private ApiClient client = PluginContext.getService(ApiClient.class);
	
	
	@Override
	public Object[] getElements(Object inputElement) {
		ApiRestReply reply = client.sendRestRequest(HttpMethod.GET, "/projects").join();
		reply.getData().forEach((key, value) -> {
			System.out.println(key);
		});
		
		// return getChildren(inputElement);
		System.out.println("THAT'S IT, I'M TRIGGERED!!!  :@ :@");
		return new String[] { "OH MY GOD", "I MADE IT", "IT WORKS", ":O" };
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		/*
		 * if (!(parentElement instanceof IProject)) return null;
		 */

		return null;
	}

	@Override
	public Object getParent(Object element) {
		// if(!(element instanceof RekoProgram))
		// return null;

		// return element
		return null;

	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

}
