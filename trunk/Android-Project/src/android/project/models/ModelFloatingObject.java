package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;

public class ModelFloatingObject extends Object2D {

	private Object2D _innerObject;
	private long _totalTime;
	private long _rotateTime;
	private float _totalRotationAngle;
	private float _prevY;
	private float _a;
	private float _yHalf;
	private long _tHalf = 200;

	public ModelFloatingObject(Object2D innerObject, float x, float y, float yHalf, long tHalf, long rotateSpeed) {
		super(null, null, new Positioning(x, y), false, false, false, null);
		_innerObject = innerObject;
		if (_innerObject == null)
			return;
		
		_rotateTime = rotateSpeed;
		_totalTime = 0;

		_prevY = 0;

		_yHalf = yHalf;
		_tHalf = tHalf;

		_a = - 2 * _yHalf / (_tHalf * _tHalf);

		_totalRotationAngle = 0;

		float initialRotation = (float) Math.random() * 360;
		rotate(initialRotation);
		innerObject.rotate(-initialRotation);
		_totalRotationAngle += initialRotation;
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
		_totalRotationAngle += rotate;
		if (_totalRotationAngle > 360)
			_totalRotationAngle -= 360;
	}

	public Object2D freeInnerObject() {
		if (_innerObject == null)
			return null;
		float tx = getPositioning().getX();
		float ty = getPositioning().getY();
		float dx = _innerObject.getX() - getX();
		float dy = _innerObject.getY() - getY();
		_innerObject.getPositioning().setX(tx + dx);
		_innerObject.getPositioning().setY(ty + dy);
		_innerObject.rotate(_totalRotationAngle);
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
