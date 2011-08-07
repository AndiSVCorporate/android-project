package android.project;

import android.graphics.Canvas;

public interface Bounds {

	public enum BoundsType {
		RECT, CIRCLE
	}
	
	public void drawBounds(Canvas c);
	
	public float getBoundingRadius();
	
	public boolean isPointInside(Object2D object, float x, float y);
	
	public BoundsType getBoundsType();
	
}
