package android.project.models;

import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelTwitterButton extends Object2DBitmap {
	
	public ModelTwitterButton() {
		super(R.drawable.twitter, new BoundsCircle(40), null, null, false, false, false, null);
		setDepth(70);
	}
}