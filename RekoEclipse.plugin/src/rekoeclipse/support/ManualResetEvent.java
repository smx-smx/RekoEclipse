/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rekoeclipse.support;

/**
 *
 * @author sm
 */
public class ManualResetEvent {

	private final Object monitor = new Object();
	private volatile boolean signaled = false;

	public ManualResetEvent(boolean signaled) {
		this.signaled = signaled;
	}
	
	public ManualResetEvent(){
		this(false);
	}

	public void waitOne() throws InterruptedException {
		synchronized (monitor) {
			while (signaled == false) {
				monitor.wait();
			}
		}
	}

	public boolean waitOne(long milliseconds) throws InterruptedException {
		synchronized (monitor) {
			if (signaled) {
				return true;
			}
			monitor.wait(milliseconds);
			return signaled;
		}
	}

	public void set() {//open start
		synchronized (monitor) {
			signaled = true;
			monitor.notifyAll();
		}
	}

	public void reset() {//close stop
		signaled = false;
	}
}
