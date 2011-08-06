package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.project.Constants;
import android.project.Object2D;

public class ModelBezierCurve extends Object2D {

	Paint _line;
	Object2D _o1;
	Object2D _o2;
	int _color1;
	int _color2;
	
	public ModelBezierCurve(Object2D o1, Object2D o2, int color1, int color2) {
		super(null, null, null, true, false, false, null);
		_o1 = o1;
		_o2 = o2;
		_color1 = color1;
		_color2 = color2;
		_line = new Paint();
		_line.setStyle(Paint.Style.STROKE);
		_line.setAntiAlias(true);
		_line.setStrokeWidth(7);
	}

	@Override
	public void drawThis(Canvas c) {
		float startX = _o1.getX();
		float startY = _o1.getY();
		float endX = _o2.getX();
		float endY = _o2.getY();
		float midX = (startX + endX) / 2;
		float midY = (startY + endY) / 2;;
		
		Path p = new Path();
		p.reset();
		p.moveTo(startX, startY);
		p.quadTo((startX + midX) / 2, startY, midX, midY);
		p.quadTo((midX + endX) / 2, endY, endX, endY);
		 
		_line.setShader(new LinearGradient(startX, startY, endX, endY, _color1, _color2, Shader.TileMode.CLAMP));
		c.drawPath(p, _line);
	}
}
