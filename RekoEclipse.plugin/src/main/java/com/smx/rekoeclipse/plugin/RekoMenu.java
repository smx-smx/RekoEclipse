 
package com.smx.rekoeclipse.plugin;

import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.di.extensions.Service;
import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import com.smx.rekoeclipse.models.MenuHierarchy;
import com.smx.rekoeclipse.services.ApiClient;

public class RekoMenu {
	@Inject
	@Service
	private ApiClient reko;
	
	@AboutToShow
	public void aboutToShow(List<MMenuElement> items) {
		MMenuFactory factory = MMenuFactory.INSTANCE;
			
		//MenuHierarchy menus = reko.getMenus();
		//System.out.println(menus.getItems().get(0).getText());	
		
		MDirectMenuItem dynamicItem = factory.createDirectMenuItem();
		dynamicItem.setLabel("FOO");
		items.add(dynamicItem);
	}
		
}