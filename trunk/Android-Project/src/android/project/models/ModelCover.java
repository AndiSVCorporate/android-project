package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;

public class ModelCover extends Object2D {

	private Paint _paint;
	private int _color;
	
	
	public ModelCover(int color) {
		super(null, null, null, true, false, false, null);
		_paint = new Paint();
		_color = color;
	}

	@Override
	public void drawThis(Canvas c) {
		_paint.setColor(_color);
		c.drawRect(0, 0, Constants.ASPECT_WIDTH, Constants.ASPECT_HEIGHT, _paint);
	}
	
	public void setColor(int color) {
		_color = color;
	}
	
	@Override
	public int depth() {
		return Constants.DEPTH_COVER;
	}
	
}
