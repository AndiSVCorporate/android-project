package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelResumeButton extends Object2DBitmap {
	
	public ModelResumeButton() {
		super(R.drawable.resume, new BoundsCircle(100), null,
				new Position(0, 0, 1, 1, 0),
				false, false, false, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xff92cc47);
		Object2D outer = new ModelCircle(100, 0, 0, paint);
		addObject(outer);
		outer.setDepth(98);
		paint.setColor(0xff9aef3f);
		Object2D inner = new ModelCircle(93, 0, 0, paint);
		addObject(inner);
		inner.setDepth(99);
		paint.setStrokeWidth(7);
		paint.setColor(0xff92cc47);
		setDepth(100);
		
	}
}
