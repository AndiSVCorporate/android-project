package android.project.models;

import android.project.Object2D;

public class ModelRotateObject extends Object2D {

	private Object2D _innerObject;
	private long _totalTime;
	private long _time;
	private float _sr;
	private float _er;
	
	public ModelRotateObject(Object2D object, float sr, float er, long time) {
		super(object);
		object.reset();
		
		_innerObject = object;
		
		_sr = sr;
		_er = er;
		_time = time;
		_totalTime = 0;
		
		_innerObject.setAngle(sr);
		addObject(_innerObject);
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime > _time)
			_totalTime = _time;
		float m = ((float)_totalTime) / _time;
		_innerObject.setAngle(_sr + m * (_er - _sr));
	}
}
