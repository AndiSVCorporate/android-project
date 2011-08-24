package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Position;

public class ModelRect extends Object2D {

	private float _w;
	private float _h;
	private Paint _p;
	
	public ModelRect(float w, float h) {
		this(w, h, 0, 0);
	}
	
	public ModelRect(float w, float h, int color) {
		this(w, h);
		_p = new Paint();
		_p.setColor(color);
	}
	
	public ModelRect(float w, float h, float x, float y) {
		super(null, null, new Position(x, y), false, false, false, null);
		_w = w;
		_h = h;
		_p = new Paint();
	}
	
	public ModelRect(float w, float h, float x, float y, int color) {
		this(w, h, x, y);
		_p.setColor(color);
	}
	
	public ModelRect(float w, float h, float x, float y, Paint p) {
		this(w, h, x, y);
		if (p != null)
			_p.set(p);
	}

	public Paint getPaint() {
		return _p;
	}
	
	@Override
	public void drawThis(Canvas c) {
		c.drawRect(0, 0, _w, _h, _p);
	}
}
