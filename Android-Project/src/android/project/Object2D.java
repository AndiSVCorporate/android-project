package android.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class Object2D {

	private ArrayList<Object2D> _objects;

	private Object2D _parent;
	
	private boolean _isAbsolute;

	private boolean _drawCenter;
	private boolean _drawBorders;
	
	private Bounds _bounds;

	private Positioning _position;
	
	private Matrix _matrixCalibration;
	private Matrix _matrixPosition;
	
	private float _x;
	private float _y;
	
	private Matrix _tempMatrix;
	
	protected Object2D(Bounds bounds,
			Positioning calibrationData, Positioning position,
			boolean isAbsolute, boolean drawCenter, boolean drawBorders, Object2D parent) {
		
		_objects = new ArrayList<Object2D>();
		_matrixPosition = new Matrix();
		_matrixCalibration = positioningToMatrix(calibrationData);
		_isAbsolute = isAbsolute;
		_drawCenter = drawCenter;
		_drawBorders = drawBorders;
		_bounds = bounds;
		_position = position;
		_parent = parent;
		_tempMatrix = new Matrix();
	}

	public void draw(Canvas c) {
		
		c.save();
		c.concat(_matrixPosition);
		c.concat(_matrixCalibration);
		
		drawThis(c);
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		if (_drawCenter)
			c.drawCircle(0, 0, 3, paint);
		if (_drawBorders && _bounds != null)
			_bounds.drawBounds(c);
		
		c.restore();
	}

	public void addObject(Object2D object) {
		object.setParent(this);
		_objects.add(object);
	}
	
	public void removeObject(Object2D object) {
		_objects.remove(object);
		object.setParent(null);
	}
	
	public List<Object2D> getObjects() {
		List<Object2D> objects = new ArrayList<Object2D>();
		getObjects(objects);		
		return objects;
	}
	
	public void getObjects(List<Object2D> objects) {
		if (objects == null)
			return;
		_matrixPosition.reset();
		
		if (!_isAbsolute)
			if (_parent != null)
				_matrixPosition.preConcat(_parent.getPositionMatrix());
		positioningToMatrix(_matrixPosition, _position);

		objects.add(this);
		for (Object2D object : _objects) {
			object.getObjects(objects);
		}
	}
	
	public void calculate() {
		long timeDiff = Utils.getTime() - Utils.getTimePrev();
		if (timeDiff == 0)
			return;
		calculate(timeDiff);
	}
	
	public void calculate(long timeDiff) {
		calculateThis(timeDiff);
		for (Object2D object : _objects) {
			object.calculate(timeDiff);
		}
	}
	
	public Object2D getParent() {
		return _parent;
	}
	
	public void setParent(Object2D parent) {
		_parent = parent;
	}
	
	public void translateX(float value) {
		if (_position == null)
			return;
		_position.setCalibrationX(_position.getCalibrationX() + value);
		updatePoints();
		
	}
	
	public void translateY(float value) {
		if (_position == null)
			return;
		_position.setCalibrationY(_position.getCalibrationY() + value);
		updatePoints();
	}
	
	public float getX() {
		return _x;
	}
	
	public float getY() {
		return _y;
	}
	
	public Matrix getPositionMatrix() {
		return _matrixPosition;
	}
	
	public int depth() {
		return 0;
	}
	
	public void updatePoints() {
		positioningToMatrix(_tempMatrix, _position);
		float[] point = {0, 0};
		_tempMatrix.mapPoints(point);
		_x = point[0];
		_y = point[1];	
	}
	
	public static void positioningToMatrix(Matrix m, Positioning p) {
		m.reset();
		if (p == null)
			return;
		m.preTranslate(p.getCalibrationX(), p.getCalibrationY());
		m.preScale(p.getCalibrationScaleX(), p.getCalibrationScaleY());
		m.preRotate(p.getCalibrationAngle());		
	}
	
	public static Matrix positioningToMatrix(Positioning p) {
		Matrix m = new Matrix();
		positioningToMatrix(m, p);
		return m;		
	}
	
	public abstract void drawThis(Canvas c);
	
	public void calculateThis(long timeDiff) { }
	
	public static class DepthComparator implements Comparator<Object2D>{

		@Override
		public int compare(Object2D object1, Object2D object2) {
			if (object1.depth() > object2.depth())
				return 1;
			if (object1.depth() < object2.depth())
				return -1;
			return 0;
		}
		
	}
	
	public static final Comparator<Object2D> DEPTH_COMPARATOR = new DepthComparator();
	public static final Comparator<Object2D> INVERSE_DEPTH_COMPARATOR = Collections.reverseOrder(new DepthComparator());

}
