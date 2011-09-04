package android.project.models;

import android.graphics.Paint;
import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelReplayButton extends Object2DBitmap {
	
	ModelCircle _outer;
	ModelCircle _inner;
	
	public ModelReplayButton() {
		super(R.drawable.replay, new BoundsCircle(100),
				null,
				null,
				false, false, false, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xff006699);
		_outer = new ModelCircle(100, 0, 0, paint);
		_outer.setDepth(98);
		addObject(_outer);
		paint.setColor(0xff00ccff);
		_inner = new ModelCircle(93, 0, 0, paint);
		_inner.setDepth(99);
		addObject(_inner);
		setDepth(100);
	}
}
