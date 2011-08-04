package android.project;

import android.os.SystemClock;
import android.util.Log;

public class CalculateThread implements Runnable {

	private CanvasRenderer _renderer;
	private Object _lock;
	private long _prevTime;
	private volatile boolean _run;
	

	public CalculateThread(CanvasRenderer renderer) {
		_renderer = renderer;
		_lock = new Object();
		_run = true;
		_prevTime = 0;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (_lock) {
				if (_run) {
					Screen activeScreen = _renderer.getActiveScreen();
					if (activeScreen != null) {
						long nextTime = SystemClock.elapsedRealtime();
						Log.d("speed", nextTime - _prevTime + "");
						activeScreen.calculate(nextTime - _prevTime);
						_prevTime = nextTime;
					}
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
		_prevTime = SystemClock.elapsedRealtime();
		_run = true;
	}

}
