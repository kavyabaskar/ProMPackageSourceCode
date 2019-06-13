package org.processmining.stream.core.interfaces;

/**
 * This interface identifies a runnable entity. A runnable entity instance which
 * can be started, paused and shut down.
 *
 * @param <T>
 *            parameters of object
 */
public abstract interface XSRunnable extends Runnable {


	void start();

	void pause();

	void interrupt();
	
	@Deprecated // use interrupt
	void stop();

	@Deprecated // use start
	void startXSRunnable();

	@Deprecated // use pause
	void pauseXSRunnable();

	@Deprecated // use interrupt
	void stopXSRunnable();

	public String getName();

	public boolean isStarted();

	public boolean isRunning();

	public boolean isPaused();

	public boolean isStopped();

}
