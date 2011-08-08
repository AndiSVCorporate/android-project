package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.util.Log;

public class ModelMoveObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _t;
	private float _sx;
	private float _sy;
	private float _ex;
	private float _ey;

	public ModelMoveObject(Object2D innerObject, float x, float y, long time) {
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
		_ex = x;
		_ey = y;
		_t = time;
		addObject(_innerObject);
	}


	@Override
	public void drawThis(Canvas c) { }

	@Override
	public void calculateThis(long timeDiff) {
		if (_innerObject == null)
			return;
		if (_totalTime >= _t)
			return;
		_totalTime = Math.min(_totalTime + timeDiff, _t);
		float tf = ((float) _totalTime / _t);
		float x = _sx + (_ex - _sx) * tf;
		float y = _sy + (_ey - _sy) * tf;
		getPositioning().setCalibrationX(x);
		getPositioning().setCalibrationY(y);
	}

	public Object2D freeInnerObject() {
		if (_innerObject == null)
			return null;
		float x = getPositioning().getCalibrationX();
		float y = getPositioning().getCalibrationY();
		_innerObject.translate(x, y);
		Object2D toReturn = _innerObject;
		removeObject(_innerObject);
		_innerObject = null;
		getParent().removeObject(this);
		return toReturn;
	}

	@Override
	public int depth() {
		return 1000;
	}
}