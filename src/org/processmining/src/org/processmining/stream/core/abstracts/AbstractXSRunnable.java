package org.processmining.stream.core.abstracts;

import org.processmining.stream.core.interfaces.XSRunnable;

public abstract class AbstractXSRunnable extends Thread implements XSRunnable {

	private boolean paused = false;
	private boolean started = false;
	private boolean stopped = false;

	public AbstractXSRunnable(String name) {
		super(name);
	}

	public void interrupt() {
		paused = false;
		stopped = true;
		super.interrupt();
	}

	@Override
	public boolean isPaused() {
		return paused && !stopped;
	}

	@Override
	public boolean isRunning() {
		return started && !paused && !stopped;
	}

	public boolean isStarted() {
		return started;
	}

	@Override
	public boolean isStopped() {
		return stopped;
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Deprecated
	public void pauseXSRunnable() {
		pause();
	}

	@Override
	public void run() {
		while (started && !stopped) {
			while (paused && !stopped) {
				Thread.yield();
			}
			workPackage();
		}
	}

	@Override
	public void start() {
		if (!started) {
			started = true;
			paused = false;
			stopped = false;
			super.start();
		} else {
			paused = false;
		}
	}
	
	@Deprecated
	public void startXSRunnable() {
		start();
	}

	@Deprecated
	public void stopXSRunnable() {
		interrupt();
	}

	protected abstract void workPackage();
}
