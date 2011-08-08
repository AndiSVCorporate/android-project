package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSettingsButton extends Object2DBitmap {
	
	public ModelSettingsButton() {
		super(R.drawable.button_settings_1, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xffffcc00);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(48);
		addObject(outer);
		p.setColor(0xffffff00);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(49);
		addObject(inner);
		setDepth(50);
	}
}