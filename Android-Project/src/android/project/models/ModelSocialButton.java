package android.project.models;

import android.graphics.Paint;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSocialButton extends Object2DBitmap {
	
	private int _depth;
	private ModelCircle _inner;
	private ModelCircle _outer;
	
	public ModelSocialButton() {
		super(R.drawable.button_social_1, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff0099ff);
		_outer = new ModelCircle(103, 40, 0, 0, p);
		addObject(_outer);
		p.setColor(0xff33ccff);
		_inner = new ModelCircle(104, 33, 0, 0, p);
		addObject(_inner);
		_depth = 105;
	}
	
	public void setDepth(int depth) {
		_depth = depth;
		_outer.setDepth(depth - 2);
		_inner.setDepth(depth - 1);
	}
	
	@Override
	public int depth() {
		return _depth;
	}
}