package android.project.models;

import android.graphics.Canvas;
import android.project.Constants;
import android.project.Object2D;

public class ModelButton extends Object2D {

	public ModelButton() {
		super(null, null, null, false, false, false, null);
	}

	@Override
	public void drawThis(Canvas c) {
		c.drawCircle(0, 0, 40, Constants.PAINT_RED);
	}
}
