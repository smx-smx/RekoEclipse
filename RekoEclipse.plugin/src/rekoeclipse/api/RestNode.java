/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.api;

import com.google.gson.Gson;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.inject.Inject;
import org.eclipse.e4.core.di.extensions.Service;
import rekoeclipse.api.protocol.ApiRestReply;
import rekoeclipse.api.protocol.HttpMethod;
import rekoeclipse.services.ApiClient;

/**
 *
 * @author sm
 * @param <T>
 */
public class RestNode<T extends JsonMappable> implements Collection<T>{
	
	@Inject
	@Service
	private ApiClient client;
	
	private final String nodeUrl;

	private ApiRestReply lastReply;
	
	private void updateLocal(){
		client.sendRestRequest(HttpMethod.GET, nodeUrl);
	}
	
	public RestNode(String nodeUrl){
		this.nodeUrl = nodeUrl;
	}

	private Collection<T> getObjects(){
		return (Collection<T>) client
				.sendRestRequest(HttpMethod.GET, nodeUrl)
				.join()
				.getData()
				.get("objects");
	}
	
	@Override
	public int size() {
		return getObjects().size();
	}

	@Override
	public boolean isEmpty() {
		return getObjects().isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return getObjects().contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return getObjects().iterator();
	}

	@Override
	public Object[] toArray() {
		return getObjects().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return getObjects().toArray(a);
	}

	@Override
	public boolean add(T e) {
		ApiRestReply reply = client.sendRestRequest(HttpMethod.POST, nodeUrl, e.getFields()).join();
		return reply.getStatusCode() == 200;
	}

	@Override
	public boolean remove(Object o) {
		ApiRestReply reply = client.sendRestRequest(HttpMethod.DELETE, nodeUrl, new HashMap<>(){{
			put("id", o.hashCode()); //TODO: is hashCode ok?
		}}).join();
		return reply.getStatusCode() == 200;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return getObjects().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		Iterator<? extends T> iterator = c.iterator();
		while(iterator.hasNext()){
			T next = iterator.next();
			if(!add(next))
				return false;
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<?> iterator = c.iterator();
		while(iterator.hasNext()){
			T next = (T) iterator.next();
			if(!remove(next))
				return false;
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	
}
