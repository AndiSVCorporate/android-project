package android.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.project.Position;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Object2D extends Position {

	private ArrayList<Object2D> _objects;

	private Object2D _parent;
	
	private boolean _isAbsolute;

	private boolean _drawCenter;
	private boolean _drawBorders;
	
	private Bounds _bounds;
	
	private Matrix _matrixCalibration;
	private Matrix _matrixPosition;
	private Matrix _matrixPositionCalc;
	
	private float _x;
	private float _y;
	
	private int _depth;
	
	/* Constructors */
	
	protected Object2D(Bounds bounds,
			Position calibrationData, Position position,
			boolean isAbsolute, boolean drawCenter, boolean drawBorders, Object2D parent) {
		super(position);
		_objects = new ArrayList<Object2D>();
		_matrixPosition = new Matrix();
		_matrixCalibration = positionToMatrix(calibrationData);
		_isAbsolute = isAbsolute;
		_drawCenter = drawCenter;
		_drawBorders = drawBorders;
		_bounds = bounds;
		_parent = parent;
		_matrixPositionCalc = new Matrix();
		_depth = 0;
	}
	
	public Object2D() {
		this(null, null, null, false, false, false, null);
	}
	
	public Object2D(Position position) {
		this(null, null, position, false, false, false, null);
	}

	/* Draw */
	
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

	/* Functions for drawing and calculating */
	
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
		for (Object2D object : _objects)
			object.getObjectsToDraw(objects);
	}
	
	public void getObjectsToCalculate(List<Object2D> objects) {
		if (objects == null)
			return;
		_matrixPositionCalc.reset();
		
		if (!_isAbsolute)
			if (_parent != null)
				_matrixPositionCalc.preConcat(_parent.getPositionMatrixCalc());
		positionToMatrix(_matrixPositionCalc, this);
		updatePoints();
		
		objects.add(this);
		if (!isCalculateChildren())
			return;
		for (Object2D object : _objects) {
			object.getObjectsToCalculate(objects);
		}
	}
	
	/* Remove and add objects */

	public void addObject(Object2D object) {
		if (object == null)
			return;
		object.setParent(this);
		_objects.add(object);
	}
	
	public List<Object2D> getObjects() {
		return _objects;
	}
	
	public boolean removeObject(Object2D object) {
		boolean removed = _objects.remove(object);
		object.setParent(null);
		return removed;
	}
	
	public void freeInnerObject(Object2D object) {
		if (!removeObject(object))
			return;
		object.merge(this);
	}
	
	public static void freeInnerObject(Object2D parent, Object2D object) {
		Object2D objectsParent = object.getParent();
		while (objectsParent != parent) {
			objectsParent.freeInnerObject(object);
			Object2D nextObjectParent = objectsParent.getParent();
			if (nextObjectParent == null)
				return;
			if (objectsParent._objects.isEmpty())
				nextObjectParent.removeObject(objectsParent);
			nextObjectParent.addObject(object);
			objectsParent = nextObjectParent;
		}
		parent.removeObject(object);
	}
	
	/* Getters and Setters */
	
	public Object2D getParent() {
		return _parent;
	}
	
	public void setParent(Object2D parent) {
		_parent = parent;
	}
	
	public Screen getScreen() {
		return getCanvasRenderer().getActiveScreen();
	}
		
	public CanvasRenderer getCanvasRenderer() {
		return Utils.getCanvasRenderer();
	}
		
	public float getRealX() {
		return _x;
	}
	
	public float getRealY() {
		return _y;
	}

	public Object2D getWorld() {
		return getScreen().getWorld();
	}
	
	public Matrix getPositionMatrix() {
		return _matrixPosition;
	}
	
	public Matrix getPositionMatrixCalc() {
		return _matrixPositionCalc;
	}
	
	public void setBounds(Bounds bounds) {
		_bounds = bounds;
	}
	
	public boolean isPointInside(float x, float y) {
		if (_bounds == null)
			return false;
		return _bounds.isPointInside(this, x, y);
	}
	
	public int depth() {
		return _depth;
	}
	
	public void setDepth(int depth) {
		_depth = depth;
	}
	
	public void setDepthRecursive(int dx) {
		_depth += dx;
		for (Object2D object : _objects) {
			object.setDepthRecursive(dx);
		}
	}
	
	public boolean isCalculateChildren() {
		return true;
	}
	
	public void updatePoints() {
		float[] point = {0, 0};
		_matrixPositionCalc.mapPoints(point);
		_x = point[0];
		_y = point[1];
	}
	
	public void drawThis(Canvas c) { }
	
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
