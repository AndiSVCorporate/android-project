package android.project;

import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.MotionEvent;

public abstract class Screen {

	private World _world;
	private CalculateThread _calculateThread;
	private CanvasRenderer _canvasRenderer;
	private List<Object2D> _toCalculate;
	
	public Screen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		_world = new World();
		_world.setScreen(this);
		_calculateThread = calculateThread;
		_canvasRenderer = canvasRenderer;
		_toCalculate = _world.getObjectsToCalculate();
	}
	
	public abstract boolean onTouchEvent(MotionEvent event);
	
	public abstract void calculateThis(long timeDiff);
	
	public abstract int getBorderColor();
	
	public void postInvalidate(CanvasRenderer c) {
		c.postInvalidate();
	}
	
	public void calculate(long timeDiff) {
		calculateThis(timeDiff);
		for (Object2D object : _toCalculate) {
			object.calculateThis(timeDiff);
		}
		
		_toCalculate = _world.getObjectsToCalculate();
	}
	
	public void onBackPressed() { }
	
	public World getWorld() {
		return _world;
	}
	
	protected CanvasRenderer getCanvasRenderer() {
		return _canvasRenderer;
	}
	
	protected void draw(Canvas c) {
		List<Object2D> allObjects;
		
		synchronized (_calculateThread.getLock()) {
			allObjects = _world.getObjectsToDraw();
		}
		
		Collections.sort(allObjects, Object2D.DEPTH_COMPARATOR);
		for (Object2D object : allObjects) {
			object.draw(c);
		}
	}
}
