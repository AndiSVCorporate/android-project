package android.project.models;

import android.project.Object2D;
import android.util.Log;

public class ModelMoveObject extends Object2D {

	private Object2D _innerObject;
	
	private long _totalTime;
	
	private long _t;
	private float _dx;
	private float _dy;

	public ModelMoveObject(Object2D innerObject, float dx, float dy, long time) {
		setX(innerObject.getX());
		setY(innerObject.getY());
		_innerObject = innerObject;
		if (_innerObject == null)
			return;	
		_innerObject.setX(0);
		_innerObject.setY(0);
		_totalTime = 0;
		_dx = dx;
		_dy = dy;
		_t = time;
		addObject(_innerObject);
	}

	@Override
	public void calculateThis(long timeDiff) {
		if (_innerObject == null)
			return;
		_totalTime += timeDiff;
		if (_totalTime > _t)
			_totalTime = _t;
		double tf = ((double) _totalTime / (double) _t);

		_innerObject.setX((float) (_dx * tf));
		_innerObject.setY((float) (_dy * tf));
	}

	@Override
	public int depth() {
		return 0;
	}
}