package android.project.models;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;

public class ModelBackground extends Object2D {

	Paint paint;
	
	public ModelBackground(int color) {
		super(null, null, null, true, false, false, null);
		paint = new Paint();
		paint.setColor(color);
	}

	@Override
	public void drawThis(Canvas c) {
		c.drawRect(0, 0, Constants.ASPECT_WIDTH, Constants.ASPECT_HEIGHT, paint);
	}
	
	@Override
	public int depth() {
		return Constants.DEPTH_BACKGROUND;
	}

}
