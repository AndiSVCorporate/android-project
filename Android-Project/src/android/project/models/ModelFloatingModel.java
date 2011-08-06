package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;
import android.util.Log;

public class ModelFloatingModel extends Object2D {
	
	private Object2D _innerObject;
	private long _totalTime;
	private long _rotateTime;
	private float _prevY;
	private float _a;
	private float _yHalf;
	private long _tHalf = 200;
	
	public ModelFloatingModel(Object2D innerObject, float x, float y, float yHalf, long tHalf, long rotateSpeed) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		_innerObject = innerObject;
		
		 _rotateTime = rotateSpeed;
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
	
}
