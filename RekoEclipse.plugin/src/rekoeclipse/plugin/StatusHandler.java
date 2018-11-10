/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.plugin;

import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.statushandlers.WorkbenchErrorHandler;
import org.eclipse.ui.statushandlers.WorkbenchStatusDialogManager;

/**
 *
 * @author sm
 */
public class StatusHandler extends WorkbenchErrorHandler {
	@Override
	protected void configureStatusDialog(WorkbenchStatusDialogManager statusDialog) {
		super.configureStatusDialog(statusDialog); //To change body of generated methods, choose Tools | Templates.
		statusDialog.enableDefaultSupportArea(true);
	}

	@Override
	public void handle(StatusAdapter statusAdapter, int style) {
		//style = StatusManager.SHOW | StatusManager.BLOCK;
		super.handle(statusAdapter, style); //To change body of generated methods, choose Tools | Templates.
	}
	
	
	
}
