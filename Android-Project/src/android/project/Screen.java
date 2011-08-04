package android.project;

import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public abstract class Screen {

	private World _world;
	private CalculateThread _calculateThread;
	private CanvasRenderer _canvasRenderer;
	
	public Screen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		_world = new World();
		_calculateThread = calculateThread;
		_canvasRenderer = canvasRenderer;
	}
	
	public abstract boolean onTouchEvent(MotionEvent event);
	
	public abstract void calculateThis(long timeDiff);
	
	public abstract int getBorderColor();
	
	public abstract void postInvalidate();
	
	public void calculate(long timeDiff) {
		if (timeDiff == 0 || timeDiff >= Constants.TIME_MAX_DIFF)
			return;
		
		calculateThis(timeDiff);
		
		List<Object2D> objects = _world.getObjectsToCalculate();
		
		for (Object2D object : objects) {
			object.calculateThis(timeDiff);
		}
	}
	
	protected World getWorld() {
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
