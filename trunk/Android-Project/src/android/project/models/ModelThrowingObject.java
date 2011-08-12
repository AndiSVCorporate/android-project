package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.util.Log;

public class ModelThrowingObject extends Object2D {
	private Object2D _innerObject;
	private long _totalTime;
	
	private long _tFall;
	private float _vX;
	private float _dy;
	private float _a;
	
	public ModelThrowingObject(Object2D innerObject, float vX, float dx, float dy) {
		super(innerObject);	
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		_innerObject.reset();
		
		_totalTime = 0;
		_vX = vX;
		_dy = dy;		
		addObject(_innerObject);
		
		_tFall = (long) (dx / _vX * 1000);
		Log.d("tFall", "" + _tFall);
		_a = -_dy /  (_tFall* _tFall);
	}

	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		if(_innerObject == null)
			return;
		if(_totalTime>=_tFall)
			return;
		_totalTime = Math.min(_totalTime+timeDiff,_tFall);
		float x = _vX * ((float)_totalTime) / 1000;
		float t = _totalTime;
		float y = _a * t * t + _dy;
		_innerObject.setX(x);
		_innerObject.setY(_dy - y);
		if(x > 1200) {
			removeObject(_innerObject);
			_innerObject = null;
			getParent().removeObject(this);
		}
		
	}

}
