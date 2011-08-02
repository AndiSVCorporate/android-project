package android.project;

public class CalculateThread implements Runnable {

	private CanvasRenderer _renderer;
	private Object _lock;
	
	public CalculateThread(CanvasRenderer renderer) {
		_renderer = renderer;
		_lock = new Object();
	}
	
	@Override
	public void run() {
		synchronized (_lock) {
			Screen activeScreen = _renderer.getActiveScreen();
			activeScreen.calculate();
		}
	}
	
	public Object getLock() {
		return _lock;
	}
	
	public void stop() {
		
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		
	}

}
