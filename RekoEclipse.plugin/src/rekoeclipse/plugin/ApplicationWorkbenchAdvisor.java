package rekoeclipse.plugin;

import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	@Override
	public String getInitialWindowPerspectiveId() {
		return "rekoeclipse.perspective";
	}

	@Override
	public synchronized AbstractStatusHandler getWorkbenchErrorHandler() {
		return new StatusHandler();
	}
}
