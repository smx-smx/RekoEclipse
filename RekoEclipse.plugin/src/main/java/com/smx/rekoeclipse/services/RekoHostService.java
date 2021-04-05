/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smx.rekoeclipse.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.RuntimeProcess;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;

import org.osgi.service.component.annotations.Component;

import rekoeclipse.plugin.OsgiServiceBase;

/**
 *
 * @author sm
 */
@Component(service = RekoHostService.class)
public class RekoHostService extends OsgiServiceBase implements AutoCloseable {
	private String rekoPath;
	private RuntimeProcess rekoProcess;
	
	private final int socketPort;
	
	public RekoHostService(){
		this.socketPort = getRandomSocketPort();
	}
	
	public int getSocketPort() {
		return socketPort;
	}
	
	@Activate
	protected void activate(BundleContext ctx){		
		this.rekoPath = ctx.getProperty("reko.path");
		this.start();
	}
	
	private static int getRandomSocketPort(){
		ServerSocket server;
		int port;
		
		try {
			server = new ServerSocket(0);
			port = server.getLocalPort();
			server.close();
		} catch (IOException ex) {
			throw new UncheckedIOException("Failed to get random socket port", ex);
		}
		return port;
	}
	
	public void start(){
		String[] args = new String[]{
			rekoPath,
			Integer.toString(socketPort)
		};
		
		// create a basic run action 
		ILaunch launch = new Launch(null, ILaunchManager.RUN_MODE, null);
		
		try {
			Process wrapperProcess = Runtime.getRuntime().exec(args);
			this.rekoProcess = new RuntimeProcess(launch, wrapperProcess, "Reko ApiServer", null);
		} catch (IOException ex) {
			throw new UncheckedIOException(
					String.format("Failed to start Reko with arguments: '%s'", String.join(" ", args)),
					ex
			);
		}
	}

	@Override
	public void close() {
		if(rekoProcess == null){
			throw new IllegalStateException("Cannot stop reko because it's not running");
		}
		
		try {
			rekoProcess.terminate();
		} catch (DebugException e) {
			throw new Error("Failed to stop Reko");
		}
	}
}
