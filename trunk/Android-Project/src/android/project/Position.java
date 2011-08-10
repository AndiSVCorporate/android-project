package android.project;

import android.graphics.Matrix;

public class Position {

	private float _x;
	private float _y;
	private float _scaleX;
	private float _scaleY;
	private float _angle;

	public Position(float x, float y,
			float scaleX, float scaleY,
			float angle) {
		_x = x;
		_y = y;
		_scaleX = scaleX;
		_scaleY = scaleY;
		_angle = angle;
	}

	public Position(Position other) {
		this();
		if (other == null) 
			return;
		_x = other.getX();
		_y = other.getY();
		_scaleX = other.getScaleX();
		_scaleY = other.getScaleY();
		_angle = other.getAngle();
	}

	public Position() {
		this(0, 0, 1, 1, 0);
	}

	public Position(float x, float y) {
		this(x, y, 1, 1, 0);
	}

	public float getX() {
		return _x;
	}

	public void setX(float x) {
		_x = x;
	}

	public float getY() {
		return _y;
	}

	public void setY(float y) {
		_y = y;
	}

	public float getScaleX() {
		return _scaleX;
	}

	public void setScaleX(float scaleX) {
		_scaleX = scaleX;
	}

	public float getScaleY() {
		return _scaleY;
	}

	public void setScaleY(float scaleY) {
		_scaleY = scaleY;
	}

	public float getAngle() {
		return _angle;
	}

	public void reset() {
		_x = 0;
		_y = 0;
		_scaleX = 1;
		_scaleY = 1;
		_angle = 0;
	}
	
	public void setAngle(float angle) {
		_angle = angle;
	}
	
	public void translateX(float x) {
		_x += x;
	}
	
	public void translateY(float y) {
		_y += y;
	}
	
	public void translate(float x, float y) {
		translateX(x);
		translateY(y);
	}
	
	public void scale(float x, float y) {
		scaleX(x);
		scaleY(y);
	}
	
	public void scaleX(float scaleX) {
		_scaleX *= scaleX;
	}
	
	public void scaleY(float scaleY) {
		_scaleY *= scaleY;
	}
	
	public void scale(float scale) {
		scale(scale, scale);
	}
	
	public void rotate(float angle) {
		_angle *= angle;
	}

	public void merge(Position other) {
		if (other == null)
			return;
		translateX(other.getX());
		translateY(other.getY());
		scaleX(other.getScaleX());
		scaleY(other.getScaleY());
		rotate(other.getAngle());
	}
	
	public static void positionToMatrix(Matrix m, Position p) {
		if (p == null)
			return;
		m.preTranslate(p.getX(), p.getY());
		m.preScale(p.getScaleX(), p.getScaleY());
		m.preRotate(p.getAngle());		
	}
	
	public static Matrix positionToMatrix(Position p) {
		Matrix m = new Matrix();
		positionToMatrix(m, p);
		return m;		
	}
}
