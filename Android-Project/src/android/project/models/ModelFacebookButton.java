package android.project.models;

import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelFacebookButton extends Object2DBitmap {
	
	public ModelFacebookButton() {
		super(R.drawable.facebook, new BoundsCircle(40), null, null, false, false, false, null);
		setDepth(70);
	}
}