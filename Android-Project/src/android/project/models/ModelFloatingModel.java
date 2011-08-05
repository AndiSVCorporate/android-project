package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;
import android.util.Log;

public class ModelFloatingModel extends Object2D {
	
	private Object2D _innerObject;
	private float _innerObjectS;
	
	public ModelFloatingModel(Object2D innerObject, float x, float y) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		_innerObject = innerObject;
		_innerObjectS = 0;
		addObject(_innerObject);
	}

	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		float acceleration = -10 + Math.max(0, _innerObject.getY() - getY());
		//Log.d("dy", getY() + " - " + _innerObject.getY());
		Log.d("acceleration", "" + acceleration);
		_innerObjectS -= acceleration * ((float) timeDiff / 1000);
		//Log.d("speed", "" + _innerObjectS);
		_innerObject.translateY(6 * _innerObjectS * ((float)timeDiff / 1000));
		//Log.d("dy", getY() + " - " + _innerObject.getY());
	}
	
	@Override
	public int depth() {
		return _innerObject.depth();
	}
	
}
