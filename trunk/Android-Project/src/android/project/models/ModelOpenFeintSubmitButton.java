package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelOpenFeintSubmitButton extends Object2DBitmap {
	
	public ModelOpenFeintSubmitButton() {
		super(R.drawable.openfeint, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff92cc47);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(58);
		
		addObject(outer);
		p.setColor(0xff9aef3f);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(59);
		addObject(inner);
		setDepth(60);
	}
}