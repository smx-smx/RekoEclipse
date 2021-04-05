package com.smx.rekoeclipse.plugin;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.smx.rekoeclipse.services.RekoHostService;

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