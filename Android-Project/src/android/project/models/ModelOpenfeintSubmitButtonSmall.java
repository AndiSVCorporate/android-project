package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelOpenfeintSubmitButtonSmall extends Object2DBitmap {
	private ModelCircle _outer;
	private ModelCircle _inner;
	
	public ModelOpenfeintSubmitButtonSmall() {
		super(R.drawable.openfeint, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff92cc47);
		_outer = new ModelCircle(40, 0, 0, p);
		_outer.setDepth(48000);
		
		addObject(_outer);
		p.setColor(0xff9aef3f);
		_inner = new ModelCircle(33, 0, 0, p);
		_inner.setDepth(48001);
		addObject(_inner);
		setDepth(48002);
	}
	public void setAlpha(int a){
		_inner.getPaint().setAlpha(a);
		_outer.getPaint().setAlpha(a);
		super.setAlpha(a);
	}
}