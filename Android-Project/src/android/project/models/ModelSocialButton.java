package android.project.models;

import android.graphics.Paint;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSocialButton extends Object2DBitmap {
	
	private ModelCircle _inner;
	private ModelCircle _outer;
	
	public ModelSocialButton() {
		super(R.drawable.button_social_1, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff0099ff);
		_outer = new ModelCircle(40, 0, 0, p);
		_outer.setDepth(58);
		addObject(_outer);
		p.setColor(0xff33ccff);
		_inner = new ModelCircle(33, 0, 0, p);
		_inner.setDepth(59);
		addObject(_inner);
		setDepth(60);
	}
}