package android.project.models;

import android.graphics.Paint;
import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSettingsButtonBig extends Object2DBitmap {
	
	ModelCircle _outer;
	ModelCircle _inner;
	
	public ModelSettingsButtonBig(int x, int y) {
		super(R.drawable.button_settings_big, new BoundsCircle(100),
				null,
				new Position(x, y, 1, 1, 0),
				false, false, false, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xffffcc00);
		_outer = new ModelCircle(100, 0, 0, paint);
		_outer.setDepth(98);
		addObject(_outer);
		paint.setColor(0xffffff00);
		_inner = new ModelCircle(93, 0, 0, paint);
		_inner.setDepth(99);
		addObject(_inner);
		setDepth(100);
	}
}
