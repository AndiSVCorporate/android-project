package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Position;

public class ModelLine extends Object2D {

	private float _x;
	private float _y;
	private Paint _p;
	
	private int _depth;
	
	public ModelLine(float dx, float dy) {
		this(dx, dy, 0, 0);
	}
	
	public ModelLine(float dx, float dy, float x, float y) {
		super(null, null, new Position(x, y), false, false, false, null);
		_x = dx;
		_y = dy;
		_p = new Paint();
		_depth = 0;
	}
	
	public ModelLine(float dx, float dy, float x, float y, int color) {
		this (dx, dy, x, y);
		_p.setColor(color);
	}
	
	public ModelLine(float dx, float dy, float x, float y, Paint p) {
		this(dx, dy, x, y);
		if (p != null)
			_p.set(p);
	}

	public Paint getPaint() {
		return _p;
	}
	
	@Override
	public void drawThis(Canvas c) {
		c.drawLine(0, 0, _x, _y, _p);
	}
	
	public void setDepth(int depth) {
		_depth = depth;
	}
	
	@Override
	public int depth() {
		return _depth;
	}
}

