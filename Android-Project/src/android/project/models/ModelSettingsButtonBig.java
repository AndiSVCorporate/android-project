package android.project.models;

import android.graphics.Paint;
import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSettingsButtonBig extends Object2DBitmap {
	
	public ModelSettingsButtonBig(int x, int y) {
		super(R.drawable.button_settings_big, new BoundsCircle(100),
				null,
				new Positioning(x, y, 1, 1, 0),
				false, false, false, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xffffcc00);
		addObject(new ModelCircle(128, 100, 0, 0, paint));
		paint.setColor(0xffffff00);
		addObject(new ModelCircle(129, 93, 0, 0, paint));
	}
	
	@Override
	public int depth() {
		return 130;
	}
}
