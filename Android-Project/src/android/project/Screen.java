package android.project;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Screen {

	public void calculate();
	
	public void draw(Canvas c);
	
	public boolean onTouchEvent(MotionEvent event);
	
	public int getBorderColor();
	
	public void addObject2D(Object2D o);
	
	public void removeObject2D(Object2D o);
	
	public void postInvalidate();
}
