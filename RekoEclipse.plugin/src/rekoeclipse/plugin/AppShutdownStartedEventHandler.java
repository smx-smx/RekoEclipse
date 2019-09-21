package rekoeclipse.plugin;

import java.util.Queue;

import javax.inject.Inject;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.e4.core.di.extensions.OSGiBundle;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import rekoeclipse.services.RekoHostService;

public class AppShutdownStartedEventHandler implements EventHandler {
	private final IEventBroker eventBroker;
	private final IApplicationContext context;
	
	
	public AppShutdownStartedEventHandler(IEventBroker eventBroker, IApplicationContext context) {
		this.eventBroker = eventBroker;
		this.context = context;
	}
	
	@Override
	public void handleEvent(Event event) {
		eventBroker.unsubscribe(this);
		RekoHostService hostSvc = PluginContext.getService(RekoHostService.class);
		hostSvc.close();
	}
}