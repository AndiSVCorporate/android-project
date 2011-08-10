package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;

public class ModelScaleObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _scaleTime;
	private float _scale0;
	private float _scaleEnd;

	public ModelScaleObject(Object2D innerObject, float scale0, float scaleEnd, long time) {
		super(innerObject);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		innerObject.reset();
		
		_totalTime = 0;
		_scale0 = scale0;
		_scaleEnd = scaleEnd;
		_scaleTime = time;
		
		addObject(_innerObject);
	}


	@Override
	public void drawThis(Canvas c) { }

	@Override
	public void calculateThis(long timeDiff) {
		if (_innerObject == null)
			return;
		if (_totalTime >= _scaleTime)
			return;
		_totalTime = Math.min(_totalTime + timeDiff, _scaleTime);
		float tf = ((float) _totalTime / _scaleTime);
		float scale = _scale0 + (_scaleEnd - _scale0) * tf;
		_innerObject.setScaleX(scale);
		_innerObject.setScaleY(scale);
	}

	@Override
	public int depth() {
		return 1000;
	}
}