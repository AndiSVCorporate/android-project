package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelFacebookButton extends Object2DBitmap {
	
	public ModelFacebookButton() {
		super(R.drawable.facebook, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff0066ff);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(58);
		addObject(outer);
		p.setColor(0xff3399ff);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(59);
		addObject(inner);
		setDepth(60);
	}
}