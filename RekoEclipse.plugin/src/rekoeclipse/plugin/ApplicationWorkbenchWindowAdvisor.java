package rekoeclipse.plugin;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.WorkbenchWindow;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }
    
    @Override
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    @Override
    public void createWindowContents(Shell shell) {
    }
    
    
    @Override
    public void postWindowCreate() {
    	IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    	IWorkbenchWindow window = configurer.getWindow();

    	// hack to hide the workbench window
    	Shell sh = window.getShell();
    	sh.setRedraw(false);
    	sh.setVisible(false);
    }
}
