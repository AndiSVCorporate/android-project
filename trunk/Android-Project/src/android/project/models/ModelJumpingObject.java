package android.project.models;

import android.project.Object2D;

public class ModelJumpingObject extends Object2D {
	private Object2D _innerObject;
	private long _totalTime;
	
	private long _tFall;
	private float _vX;
	private float _dy;
	private float _sY;
	private float _a;
	private long _timeOffset;
	
	public ModelJumpingObject(Object2D innerObject, long tFall, float xHalf, float dy, long timeOffset) {
		super(innerObject);	
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		
		_innerObject.reset();
		_vX = (xHalf / tFall) * 1000;
		_dy = dy;		
		addObject(_innerObject);
		
		_tFall = tFall;
		_totalTime = timeOffset;
		_timeOffset = timeOffset;
		_a = -_dy /  (_tFall* _tFall);
		
		float t = timeOffset - _tFall;
		_sY = _dy - (_a * t * t + _dy); 
	}

	public long getTime() {
		return _totalTime;
	}

	@Override
	public void calculateThis(long timeDiff) {
		if(_innerObject == null)
			return;
		if(_totalTime >= 2 * _tFall)
			return;
		_totalTime += timeDiff;
		if(_totalTime >= 2 * _tFall)
			return;

		float x = _vX * ((float) (_totalTime - _timeOffset)) / 1000;
		float t = _totalTime - _tFall;
		float y = _a * t * t + _dy;
		_innerObject.setX(x);
		
		_innerObject.setY(_dy - y - _sY);
		if(x > 1200) {
			removeObject(_innerObject);
			_innerObject = null;
			getParent().removeObject(this);
		}
		
	}
	public boolean isFinished(){
		return _totalTime >= 2 * _tFall;
	}
	
	public void finalizePosition() {
		long time = _totalTime - 2 * _tFall;
		float x = _vX * ((float) (_totalTime - _timeOffset)) / 1000;
		float t = time - _tFall;
		float y = _a * t * t + _dy;
		
		_innerObject.setX(x);
		_innerObject.setY(_dy - y - _sY);
	}

	public long getTFall() {
		return _tFall;
	}
	
}
