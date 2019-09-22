package rekoeclipse.content;

import javax.inject.Inject;

import org.eclipse.e4.core.di.extensions.Service;
import org.eclipse.jface.viewers.ITreeContentProvider;
import rekoeclipse.services.ApiClient;

//import rekoeclipse.services.ApiClient;

public class ProgramContentProvider implements ITreeContentProvider {

	@Inject
	@Service
	private ApiClient client;
		
	@Override
	public Object[] getElements(Object inputElement) {
		//ApiRestReply reply = client.sendRestRequest(HttpMethod.GET, "/projects").join();
		//reply.getData().forEach((key, value) -> {
		//	System.out.println(key);
		//});
		
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
