package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelQuitConfirmButton extends Object2DBitmap {
	
	public ModelQuitConfirmButton() {
		super(R.drawable.button_quit2, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff8d2036);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(68);
		addObject(outer);
		p.setColor(0xffce0a31);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(69);
		addObject(inner);
		setDepth(70);
	}
}