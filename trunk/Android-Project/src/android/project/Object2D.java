package android.project;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Object2D {

	protected ArrayList<Object2D> _objects;

	private boolean _isAbsolute;

	private boolean _drawCenter;
	private boolean _drawBorders;
	
	private Bounds _bounds;
	
	private CalibrationData _calibrationData;
	private CalibrationData _position;
	
	protected Object2D(Bounds bounds,
			CalibrationData calibrationData, CalibrationData position,
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
				c.setMatrix(Utils.getBaseCalibrationMatrix());
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
		
		if (_position != null) {
			c.restore();
		}

	}

	protected abstract void drawThis(Canvas c);

}
