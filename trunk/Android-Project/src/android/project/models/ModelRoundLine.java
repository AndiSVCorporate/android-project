package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Positioning;

public class ModelRoundLine extends Object2D {

	private float _x;
	private float _y;
	private Paint _p;
	
	public ModelRoundLine(float dx, float dy) {
		this(dx, dy, 0, 0);
	}
	
	public ModelRoundLine(float dx, float dy, float x, float y) {
		super(null, null, new Positioning(x, y), false, false, false, null);
		_x = dx;
		_y = dy;
		_p = new Paint();		
	}
	
	public ModelRoundLine(float dx, float dy, float x, float y, int color) {
		this(dx, dy, x, y);
		_p.setColor(color);
	}
	
	public ModelRoundLine(float dx, float dy, float x, float y, Paint p) {
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
		c.drawCircle(0, 0, _p.getStrokeWidth() / 2 - 1, _p);
		c.drawCircle(_x, _y, _p.getStrokeWidth() / 2 - 1, _p);
	}
}

