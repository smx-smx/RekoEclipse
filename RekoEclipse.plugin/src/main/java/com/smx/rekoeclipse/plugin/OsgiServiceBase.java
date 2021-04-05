package com.smx.rekoeclipse.plugin;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public abstract class OsgiServiceBase {
	public OsgiServiceBase() {
		BundleContext context = InternalPlatform.getDefault().getBundleContext();
		Class<OsgiServiceBase> clazz = (Class<OsgiServiceBase>) this.getClass();
		context.registerService(clazz, this, null);
	}
}
