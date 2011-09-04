package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.project.Object2D;
import android.project.Position;

public class ModelRoundRect extends Object2D {

	private float _w;
	private float _h;
	private Paint _p;
	
	public ModelRoundRect(float w, float h) {
		this(w, h, 0, 0);
	}
	
	public ModelRoundRect(float w, float h, int color) {
		this(w, h);
		_p = new Paint();
		_p.setColor(color);
	}
	
	public ModelRoundRect(float w, float h, float x, float y) {
		super(null, null, new Position(x, y), false, false, false, null);
		_w = w;
		_h = h;
		_p = new Paint();
	}
	
	public ModelRoundRect(float w, float h, float x, float y, int color) {
		this(w, h, x, y);
		_p.setColor(color);
	}
	
	public ModelRoundRect(float w, float h, float x, float y, Paint p) {
		this(w, h, x, y);
		if (p != null)
			_p.set(p);
	}

	public Paint getPaint() {
		return _p;
	}
	
	@Override
	public void drawThis(Canvas c) {
//		_p.setColor(0xffffcc00);
		_p.setColor(Color.BLACK);
		_p.setAntiAlias(true);
		_p.setStrokeWidth(7);
		_p.setStyle(Paint.Style.STROKE);
		c.drawRoundRect(new RectF(0,0,_w,_h),40, 40,_p);
		_p.setStyle(Paint.Style.FILL);
//		_p.setColor(0xffa40778);
		_p.setColor(Color.WHITE);
		_p.setAlpha(230);
		c.drawRoundRect(new RectF(7,7,_w-7,_h-7),40, 40,_p);
	}
	
	public void setHeight(float h){
		_h=h;
	}
	
	public float getHeight(){
		return _h;
	}
}
