package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Positioning;

public class ModelRoundLine extends Object2D {

	private float _x;
	private float _y;
	private Paint _p;
	
	private int _depth;
	
	public ModelRoundLine(int depth, float dx, float dy) {
		this(depth, dx, dy, 0, 0);
	}
	
	public ModelRoundLine(int depth, float dx, float dy, float x, float y) {
		super(null, null, new Positioning(x, y), false, false, false, null);
		_x = dx;
		_y = dy;
		_p = new Paint();
		_depth = depth;
	}
	
	public ModelRoundLine(int depth, float dx, float dy, float x, float y, int color) {
		this(depth, dx, dy, x, y);
		_p.setColor(color);
	}
	
	public ModelRoundLine(int depth, float dx, float dy, float x, float y, Paint p) {
		this(depth, dx, dy, x, y);
		if (p != null)
			_p.set(p);
	}

	public Paint getPaint() {
		return _p;
	}
	
	@Override
	public void drawThis(Canvas c) {
		c.drawLine(0, 0, _x, _y, _p);
		c.drawCircle(0, 0, _p.getStrokeWidth() / 2 - 1, _p);
		c.drawCircle(_x, _y, _p.getStrokeWidth() / 2 - 1, _p);
	}
	
	public void setDepth(int depth) {
		_depth = depth;
	}
	
	@Override
	public int depth() {
		return _depth;
	}
}

