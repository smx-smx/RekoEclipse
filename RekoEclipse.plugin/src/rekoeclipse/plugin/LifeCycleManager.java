package rekoeclipse.plugin;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.service.component.annotations.Component;

public class LifeCycleManager {
	@PostContextCreate
	void postContextCreate(final IEventBroker eventBroker, IApplicationContext context) {
		eventBroker.subscribe(UIEvents.UILifeCycle.APP_SHUTDOWN_STARTED, new AppShutdownStartedEventHandler(eventBroker, context));
	}
}
