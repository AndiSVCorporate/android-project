package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;

public class ModelScaleObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _t;
	private float _scale0;
	private float _scaleTime;
	
	private float _lastScale;

	public ModelScaleObject(Object2D innerObject, float scale0, float scaleTime, long time) {
		super(null, null, null, false, false, false, null);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		_totalTime = 0;
		_scale0 = scale0;
		_scaleTime = scaleTime;
		_lastScale = 1;
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
		float scale = _scale0 + (_scaleTime - _scale0) * tf;
		_innerObject.scale(scale / _lastScale);
		_lastScale = scale;
	}

	@Override
	public int depth() {
		return 1000;
	}
}