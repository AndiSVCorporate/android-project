package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelFacebookSubmitButtonSmall extends Object2DBitmap {

	private ModelCircle _outer;
	private ModelCircle _inner;
	
	public ModelFacebookSubmitButtonSmall() {
		super(R.drawable.facebook, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff0066ff);
		_outer = new ModelCircle(40, 0, 0, p);
		_outer.setDepth(48000);
		
		addObject(_outer);
		p.setColor(0xff3399ff);
		_inner = new ModelCircle(33, 0, 0, p);
		_inner.setDepth(48001);
		addObject(_inner);
		setDepth(50000);
	}
	public void setAlpha(int a){
		_inner.getPaint().setAlpha(a);
		_outer.getPaint().setAlpha(a);
		super.setAlpha(a);
	}

}