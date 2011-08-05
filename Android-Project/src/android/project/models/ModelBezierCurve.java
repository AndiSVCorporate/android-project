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
	
	public ModelBezierCurve(int color) {
		super(null, null, null, true, false, false, null);
		_line = new Paint();
		_line.setStyle(Paint.Style.STROKE);
		_line.setAntiAlias(true);
		_line.setStrokeWidth(7);
		//_line.setColor(color);
		 
		_line.setShader(new LinearGradient(100, 100, 200, 200, Color.BLACK, Color.WHITE, Shader.TileMode.CLAMP));

		
	}

	@Override
	public void drawThis(Canvas c) {
		
		Point start = new Point(100, 100);
		Point end = new Point(200, 200);
		Point mid = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
		
		Path p = new Path();
		p.reset();
		p.moveTo(start.x, start.y);
		p.quadTo((start.x + mid.x) / 2, start.y, mid.x, mid.y);
		p.quadTo((mid.x + end.x) / 2, end.y, end.x, end.y);
		 
		c.drawPath(p, _line);
		
		Paint p1 = new Paint();
		p1.setColor(0xff92cc47);
		//c.drawCircle(260, 315, 110, p1);
		p1.setColor(0xff9aef3f);
		//c.drawCircle(260, 315, 103, p1);
	}
}
