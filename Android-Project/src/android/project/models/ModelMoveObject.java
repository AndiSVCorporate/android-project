package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;

public class ModelMoveObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _t;
	private float _dx;
	private float _dy;

	public ModelMoveObject(Object2D innerObject, float dx, float dy, long time) {
		super(innerObject);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;	
		_innerObject.reset();
		_totalTime = 0;
		_dx = dx;
		_dy = dy;
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
		_innerObject.setX(_dx * tf);
		_innerObject.setY(_dy * tf);
	}

	@Override
	public int depth() {
		return 0;
	}
}