package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Positioning;

public class ModelCircle extends Object2D {

	private float _r;
	private Paint _p;
	
	private int _depth;
	
	public ModelCircle(int depth, float r) {
		this(depth, r, 0, 0);
	}
	
	public ModelCircle(int depth, float r, float x, float y) {
		super(null, null, new Positioning(x, y), false, false, false, null);
		_r = r;
		_p = new Paint();
		_depth = depth;
	}
	
	public ModelCircle(int depth, float r, float x, float y, int color) {
		this(depth, r, x, y);
		_p.setColor(color);
	}
	
	public ModelCircle(int depth, float r, float x, float y, Paint p) {
		this(depth, r, x, y);
		if (p != null)
			_p.set(p);
	}

	public Paint getPaint() {
		return _p;
	}
	
	@Override
	public void drawThis(Canvas c) {
		c.drawCircle(0, 0, _r, _p);
	}
	
	public void setDepth(int depth) {
		_depth = depth;
	}
	
	@Override
	public int depth() {
		return _depth;
	}
}
