package android.project;

import android.os.SystemClock;

public class CalculateThread implements Runnable {

	private CanvasRenderer _renderer;
	private Object _lock;
	private volatile boolean _run;

	public CalculateThread(CanvasRenderer renderer) {
		_renderer = renderer;
		_lock = new Object();
		_run = true;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (_lock) {
				if (_run) {
					Screen activeScreen = _renderer.getActiveScreen();
					Utils.setTime(SystemClock.elapsedRealtime());
					activeScreen.calculate();
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) { }
		}
	}

	public Object getLock() {
		return _lock;
	}

	public void pause() {
		_run = false;
	}

	public void resume() {
		_run = true;
	}

}
