package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;

public class ModelThrownObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _tHalf;
	private float _yHalf;
	private float _speedX;
	private float _sx;
	private float _sy;

	public ModelThrownObject(Object2D innerObject, float speedX, long tHalf, float yHalf) {
		super(null, null, null, false, false, false, null);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		// steal (x,y) of inner object
		_sx = _innerObject.getPositioning().getCalibrationX();
		_sy = _innerObject.getPositioning().getCalibrationY();
		getPositioning().setCalibrationX(_sx);
		getPositioning().setCalibrationY(_sy);
		_innerObject.getPositioning().setCalibrationX(0);
		_innerObject.getPositioning().setCalibrationY(0);
		
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
		getPositioning().setCalibrationX(_sx + x);
		getPositioning().setCalibrationY(_sy - y);
	}

	public Object2D freeInnerObject() {
		if (_innerObject == null)
			return null;
		float x = getPositioning().getCalibrationX();
		float y = getPositioning().getCalibrationY();
		_innerObject.getPositioning().setCalibrationX(x);
		_innerObject.getPositioning().setCalibrationY(y);
		Object2D toReturn = _innerObject;
		removeObject(_innerObject);
		_innerObject = null;
		return toReturn;
	}

	@Override
	public int depth() {
		return 1000;
	}
}
