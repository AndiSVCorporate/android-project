package android.project;

import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public abstract class Screen {

	protected World _world;
	private CalculateThread _calculateThread;
	
	public Screen(CalculateThread calculateThread) {
		_world = new World();
		_calculateThread = calculateThread;
	}
	
	public abstract boolean onTouchEvent(MotionEvent event);
	
	public abstract int getBorderColor();
	
	public abstract void postInvalidate();
	
	public void calculate() {
		_world.calculate();
	}
	
	protected void draw(Canvas c) {
		List<Object2D> allObjects;
		
		synchronized (_calculateThread.getLock()) {
			allObjects = _world.getObjects();
		}
		
		Collections.sort(allObjects, Object2D.DEPTH_COMPARATOR);
		for (Object2D object : allObjects) {
			object.draw(c);
		}
	}
}
