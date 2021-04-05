/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smx.rekoeclipse.plugin;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author sm
 */
public class PluginContext {

	public static <T> T getService(Class<T> clazz) {
		BundleContext context = InternalPlatform
				.getDefault()
				.getBundleContext();

		ServiceReference ref = context.getServiceReference(clazz);
		return (T) context.getService(ref);
	}
}
