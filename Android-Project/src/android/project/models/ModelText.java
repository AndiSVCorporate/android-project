package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Utils;

public class ModelText extends Object2D {
	
	private String _text;
	private Paint _paint;
	
	public ModelText(String text) {
		this(text, new Paint());
	}
	
	public ModelText(String text, int color, float size) {
		this(text);
		_paint.setColor(color);
		_paint.setTextSize(size);
		_paint.setTypeface(Utils.getTypeface());
	}
	
	public ModelText(String text, Paint paint) {
		_text = text;
		_paint = paint;
	}
	
	@Override
	public void drawThis(Canvas c) {
		c.drawText(_text, 0, 0, _paint);
	}
	
	public Paint getPaint() {
		return _paint;
	}
	
}
