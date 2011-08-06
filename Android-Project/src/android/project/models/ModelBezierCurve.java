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
import android.project.Utils;

public class ModelBezierCurve extends Object2D {

	Paint _line;
	Object2D _o1;
	Object2D _o2;
	int _color1;
	int _color2;
	float _r1;
	float _r2;
	float[] _out;
	
	public ModelBezierCurve(Object2D o1, Object2D o2, int color1, int color2, float r1, float r2) {
		super(null, null, null, true, false, false, null);
		_o1 = o1;
		_o2 = o2;
		_color1 = color1;
		_color2 = color2;
		_r1 = r1;
		_r2 = r2;
		_out = new float[2];
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
		
		float dx = (startX - endX);
		float dy = (startY - endY);
		float d_squared = dx*dx + dy*dy;
		float r12 = _r1 + _r2;
		if (r12 * r12 >= d_squared)
			return;
		
		findPoints(startX, startY, endX, endY, _r1 - 5, _out);
		float sx = _out[0];
		float sy = _out[1];
		findPoints(endX, endY, startX, startY, _r2 - 5, _out);
		float ex = _out[0];
		float ey = _out[1];
		
		float mx = (sx + ex) / 2;
		float my = (sy + ey) / 2;;
		
		Path p = new Path();
		p.reset();
		p.moveTo(sx, sy);
		p.quadTo((sx + mx) / 2, sy, mx, my);
		p.quadTo((mx + ex) / 2, ey, ex, ey);
		 
		_line.setShader(new LinearGradient(sx, sy, ex, ey, _color1, _color2, Shader.TileMode.CLAMP));
		c.drawPath(p, _line);
	}
	
	private void findPoints(float sx, float sy, float ex, float ey, float l, float[] out) {
		
		if (Utils.floatCompare(sx, ex) == 0) {
			out[0] = sx;
			if (sx > ex)
				out[1] = sy - l;
			else
				out[1] = sy + l;
			return;
		}
		
		float dx = ex - sx;
		float dy = ey - sy;
		
		double atan = Math.atan(dy / dx);
		double cos = Math.cos(atan);
		
		float x = (float) cos * l;
		float y = (dy / dx) * x;
		
		if (dx < 0) {
			x = -x;
			y = -y;
		}
		
		out[0] = sx + x;
		out[1] = sy + y;
	}
}
