package android.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public abstract class Object2D {

	protected ArrayList<Object2D> _objects;

	private boolean _isAbsolute;

	private boolean _drawCenter;
	private boolean _drawBorders;
	
	private Bounds _bounds;
	
	private Positioning _calibrationData;
	private Positioning _position;
	
	private float _x;
	private float _y;
	
	protected Object2D(Bounds bounds,
			Positioning calibrationData, Positioning position,
			boolean isAbsolute, boolean drawCenter, boolean drawBorders) {
		_objects = new ArrayList<Object2D>();

		_isAbsolute = isAbsolute;

		_drawCenter = drawCenter;
		_drawBorders = drawBorders;

		_bounds = bounds;
		
		_calibrationData = calibrationData;
		
		_position = position;
	}

	public void draw(Canvas c) {
		
		if (_position != null) {
			c.save();
			if (_isAbsolute) {
				c.setMatrix(new Matrix());
				Log.d("mat", c.getMatrix().toShortString());
				c.setMatrix(Utils.getCanvasCalibrationMatrix());
			}
			c.translate(_position.getCalibrationX(), _position.getCalibrationY());
			c.scale(_position.getCalibrationScaleX(), _position.getCalibrationScaleY());
			c.rotate(_position.getCalibrationAngle());
		}
		
		for (Object2D object : _objects) {
			object.draw(c);
		}
		
		if (_calibrationData != null) {
			c.save();
			c.translate(_calibrationData.getCalibrationX(), _calibrationData.getCalibrationY());
			c.scale(_calibrationData.getCalibrationScaleX(), _calibrationData.getCalibrationScaleY());
			c.rotate(_calibrationData.getCalibrationAngle());
		}
		
		drawThis(c);
		
		if (_calibrationData != null) {
			c.restore();
		}

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		if (_drawCenter)
			c.drawCircle(0, 0, 3, paint);
		if (_drawBorders && _bounds != null)
			_bounds.drawBounds(c);
		
		float[] point = {0, 0};
		
		Utils.mapPoints(c, point);
		
		_x = point[0];
		_y = point[1];
		
		if (_position != null) {
			c.restore();
		}
	}

	public void calculate() { }
	
	public void translateX(float value) {
		if (_position == null)
			return;
		_position.setCalibrationX(_position.getCalibrationX() + value);
	}
	
	public void translateY(float value) {
		if (_position == null)
			return;
		_position.setCalibrationY(_position.getCalibrationY() + value);
	}
	
	public float getX() {
		return _x;
	}
	
	public float getY() {
		return _y;
	}
	
	public int depth() {
		return 0;
	}
	
	public abstract void drawThis(Canvas c);
	
	public static class DepthComparator implements Comparator<Object2D>{

		@Override
		public int compare(Object2D object1, Object2D object2) {
			return object1.depth() - object2.depth();
		}
		
	}
	
	public static final Comparator<Object2D> DEPTH_COMPARATOR = new DepthComparator();
	public static final Comparator<Object2D> INVERSE_DEPTH_COMPARATOR = Collections.reverseOrder(new DepthComparator());

}
