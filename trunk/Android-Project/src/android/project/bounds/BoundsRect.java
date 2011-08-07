package android.project.bounds;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Bounds;
import android.project.Object2D;

public class BoundsRect implements Bounds {
	
	private float _x0;
	private float _y0;
	private float _x1;
	private float _y1;
	private float _x2;
	private float _y2;
	
	private float _boundingRadius;
	
	public BoundsRect(float x0, float y0, float x1, float y1, float x2, float y2) {
		_x0 = x0;
		_y0 = y0;
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
		_boundingRadius = (float) Math.sqrt((_x0 - _x2) * (_x0 - _x2) + (_y0 - _y2) * (_y0 - _y2));
	}
	
	public BoundsRect(float width, float height) {
		this(- width / 2, - height / 2, width / 2, - height / 2, width /2, height / 2);
	}

	@Override
	public void drawBounds(Canvas c) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		
		c.drawLine(_x0, _y0, _x1, _y1, paint);
		c.drawLine(_x1, _y1, _x2, _y2, paint);
		c.drawLine(_x2, _y2, _x2 - (_x1 - _x0), _y2 - (_y1 - _y0), paint);
		c.drawLine(_x2 - (_x1 - _x0), _y2 - (_y1 - _y0), _x0, _y0, paint);
	}

	@Override
	public BoundsType getBoundsType() {
		return BoundsType.RECT;
	}

	@Override
	public float getBoundingRadius() {
		return _boundingRadius;
	}

	@Override
	public boolean isPointInside(Object2D object, float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}
}
