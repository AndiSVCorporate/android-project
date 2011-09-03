package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelOpenFeintButton extends Object2DBitmap {
	
	public ModelOpenFeintButton() {
		super(R.drawable.openfeint, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff0066ff);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(68);
		
		addObject(outer);
		p.setColor(0xff3399ff);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(69);
		addObject(inner);
		setDepth(70);
	}
}