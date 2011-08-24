package android.project.models;

import android.graphics.Paint;
import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSocialButtonBig extends Object2DBitmap {
	
	ModelCircle _outer;
	ModelCircle _inner;
	
	public ModelSocialButtonBig(int x, int y) {
		super(R.drawable.button_social_big, new BoundsCircle(100),
				null,
				new Position(x, y),
				false, false, false, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xff0099ff);
		_outer = new ModelCircle(100, 0, 0, paint);
		_outer.setDepth(98);
		addObject(_outer);
		paint.setColor(0xff33ccff);
		_inner = new ModelCircle(93, 0, 0, paint);
		_inner.setDepth(99);
		addObject(_inner);
		setDepth(100);
	}
}
