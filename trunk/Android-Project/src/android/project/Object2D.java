package android.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public abstract class Object2D {

	private ArrayList<Object2D> _objects;

	private Object2D _parent;
	
	private Screen _screen;
	
	private boolean _isAbsolute;

	private boolean _drawCenter;
	private boolean _drawBorders;
	
	private Bounds _bounds;

	private Positioning _position;
	
	private Matrix _matrixCalibration;
	private Matrix _matrixPosition;
	private Matrix _matrixPositionCalc;
	
	private float _x;
	private float _y;
	
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
		_screen = null;
		_matrixPositionCalc = new Matrix();
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
		object.setScreen(getScreen());
		_objects.add(object);
	}
	
	public void removeObject(Object2D object) {
		_objects.remove(object);
		object.setScreen(null);
		object.setParent(null);
	}
	
	public List<Object2D> getObjectsToDraw() {
		List<Object2D> objects = new ArrayList<Object2D>();
		getObjectsToDraw(objects);		
		return objects;
	}
	
	public List<Object2D> getObjectsToCalculate() {
		List<Object2D> objects = new ArrayList<Object2D>();
		getObjectsToCalculate(objects);		
		return objects;
	}
	
	public void getObjectsToDraw(List<Object2D> objects) {
		if (objects == null)
			return;
		_matrixPosition.set(_matrixPositionCalc);
		objects.add(this);
		for (Object2D object : _objects) {
			object.getObjectsToDraw(objects);
		}
	}
	
	public void getObjectsToCalculate(List<Object2D> objects) {
		if (objects == null)
			return;
		_matrixPositionCalc.reset();
		
		if (!_isAbsolute)
			if (_parent != null)
				_matrixPositionCalc.preConcat(_parent.getPositionMatrixCalc());
		positioningToMatrix(_matrixPositionCalc, _position);
		updatePoints();
		
		objects.add(this);
		for (Object2D object : _objects) {
			object.getObjectsToCalculate(objects);
		}
	}
	
	public Object2D getParent() {
		return _parent;
	}
	
	public void setParent(Object2D parent) {
		_parent = parent;
	}
	
	public Screen getScreen() {
		return _screen;
	}
	
	public void setScreen(Screen screen) {
		_screen = screen;
	}
	
	public CanvasRenderer getCanvasRenderer() {
		if (_screen == null)
			return null;
		return _screen.getCanvasRenderer();
	}
	
	public void translateX(float value) {
		if (_position == null)
			_position = new Positioning(0, 0, 1, 1, 0);
		_position.setCalibrationX(_position.getCalibrationX() + value);
	}
	
	public void translateY(float value) {
		if (_position == null)
			_position = new Positioning(0, 0, 1, 1, 0);
		_position.setCalibrationY(_position.getCalibrationY() + value);
	}
	
	public void translate(float dx, float dy) {
		_position.setCalibrationX(_position.getCalibrationX() + dx);
		_position.setCalibrationY(_position.getCalibrationY() + dy);
	}
	
	public float getX() {
		return _x;
	}
	
	public float getY() {
		return _y;
	}
	
	public Object2D getWorld() {
		Object2D world = this;
		while (world.getParent() != null) {
			world = world.getParent();
		}
		return world;
	}
	
	public Matrix getPositionMatrix() {
		return _matrixPosition;
	}
	
	public Matrix getPositionMatrixCalc() {
		return _matrixPositionCalc;
	}
	
	public int depth() {
		return 0;
	}
	
	public void updatePoints() {
		float[] point = {0, 0};
		_matrixPositionCalc.mapPoints(point);
		_x = point[0];
		_y = point[1];	
	}
	
	public static void positioningToMatrix(Matrix m, Positioning p) {
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
