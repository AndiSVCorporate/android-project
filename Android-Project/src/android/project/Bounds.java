package android.project;

import android.graphics.Canvas;

public interface Bounds {

	public enum BoundsType {
		RECT, CIRCLE
	}
	
	public void drawBounds(Canvas c);
	
	public float getBoundingRadius();
	
	public BoundsType getBoundsType();
	
}
