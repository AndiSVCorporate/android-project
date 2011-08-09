package android.project.models;

import android.graphics.Canvas;
import android.project.Constants;
import android.project.Object2D;

public class ModelJumpingObject extends Object2D {
	private Object2D _innerObject;
	private long _totalTime;
	
	private long _tFall;
	private float _vX;
	private float _sY;
	private float _sX;
	private float _dx;
	private float _dy;
	private float _a;
	
	public ModelJumpingObject(Object2D innerObject, float vX, float dx, float dy) {
		super(null, null, null, false, false, false, null);	
		_vX = vX;
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		_sX = innerObject.getPositioning().getX();
		_sY = innerObject.getPositioning().getY();
		getPositioning().setX(_sX);
		getPositioning().setY(_sY);
		_innerObject.getPositioning().setX(0);
		_innerObject.getPositioning().setY(0);
		_totalTime = 0;
		_dx = dx;
		_dy = dy;		
		addObject(_innerObject);
		
		_tFall = (long) (_vX / _dx);
		_a = -_dy /  (_tFall* _tFall);
	}

	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		if(_innerObject == null)
			return;
		_totalTime += timeDiff;
		if (_totalTime > 2 * _tFall)
			_totalTime -= 2 * _tFall;
		float x = _sX + _vX * ((float)_totalTime) / 1000;
		float t = _totalTime;
		if (t > _tFall)
			t -= 2 * _tFall;
		float y = (-_dy / (_tFall * _tFall)) * t * t + _dy;
		getPositioning().setX(x);
		getPositioning().setY(_sY + _dy - y);
		if(x > 1200) {
			removeObject(_innerObject);
			_innerObject=null;
			getParent().removeObject(this);
		}
		
	}

}
