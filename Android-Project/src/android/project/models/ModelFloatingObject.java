package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Position;

public class ModelFloatingObject extends Object2D {

	private Object2D _innerObject;
	private long _totalTime;
	private long _rotateTime;
	private float _prevY;
	private float _a;
	private float _yHalf;
	private long _tHalf = 200;

	public ModelFloatingObject(Object2D innerObject, float yHalf, long tHalf, long rotateTime) {
		super(innerObject);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		_innerObject.reset();
		
		_rotateTime = rotateTime;
		_totalTime = 0;

		_prevY = 0;

		_yHalf = yHalf;
		_tHalf = tHalf;

		_a = - 2 * _yHalf / (_tHalf * _tHalf);

		float initialRotation = (float) Math.random() * 360;
		rotate(initialRotation);
		innerObject.rotate(-initialRotation);
		
		addObject(_innerObject);
	}

	@Override
	public void drawThis(Canvas c) { }

	@Override
	public void calculateThis(long timeDiff) {
		if (_innerObject == null)
			return;
		_totalTime += timeDiff;
		if (_totalTime > 4 * _tHalf)
			_totalTime -= 4 * _tHalf;
		long t = _totalTime;
		float sign = 1;
		if (t > _tHalf * 2) {
			sign = -1;
			t -= _tHalf * 2;
		}
		t -= _tHalf;
		float y = (t * t * _a / 2 + _yHalf) * sign;
		_innerObject.translateY(y - _prevY);
		_prevY = y;

		float rotate = (float) timeDiff / _rotateTime * 360;
		rotate(rotate);
		_innerObject.rotate(-rotate);
	}

	@Override
	public int depth() {
		return 1000;
	}
}
