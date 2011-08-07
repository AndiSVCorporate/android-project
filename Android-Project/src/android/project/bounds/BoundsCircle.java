package android.project.bounds;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Bounds;
import android.project.Object2D;

public class BoundsCircle implements Bounds {

	private float _radius;
	
	public BoundsCircle(float radius) {
		_radius = radius;
	}

	@Override
	public void drawBounds(Canvas c) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		
		c.drawCircle(0, 0, _radius, paint);
	}

	@Override
	public BoundsType getBoundsType() {
		return BoundsType.CIRCLE;
	}

	@Override
	public float getBoundingRadius() {
		return _radius;
	}

	@Override
	public boolean isPointInside(Object2D object, float x, float y) {
		if (object == null)
			return false;
		float dx = object.getX() - x;
		float dy = object.getY() - y;
		return dx * dx + dy * dy < _radius * _radius;
	}
}
