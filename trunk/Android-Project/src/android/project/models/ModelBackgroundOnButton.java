package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelBackgroundOnButton extends Object2DBitmap {
	
	public ModelBackgroundOnButton() {
		super(R.drawable.background, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xffd70044);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(58);
		addObject(outer);
		p.setColor(0xffff6666);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(59);
		addObject(inner);
		setDepth(60);
	}
}
