package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;

public class ModelThrownObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _tHalf;
	private float _yHalf;
	private float _speedX;
	
	public ModelThrownObject(Object2D innerObject, float speedX, long tHalf, float yHalf) {
		super(innerObject);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		_innerObject.reset();
		// steal (x,y) of inner object
		
		_totalTime = 0;
		_tHalf = tHalf;
		_yHalf = yHalf;
		_speedX = speedX;
		addObject(_innerObject);
	}


	@Override
	public void drawThis(Canvas c) { }

	@Override
	public void calculateThis(long timeDiff) {
		if (_innerObject == null)
			return;
		_totalTime += timeDiff;
		float t = _totalTime - _tHalf;
		float y = -_yHalf * t * t / (_tHalf * _tHalf) + _yHalf;
		float x = _speedX * ((float) _totalTime / 1000);
		_innerObject.setX(x);
		_innerObject.setY(-y);
		if (getRealY() > 800) {
			getParent().removeObject(this);
		}
	}
	
	@Override
	public int depth() {
		return 1000;
	}
}
