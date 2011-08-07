package android.project.models;

import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSettingsButton extends Object2DBitmap {
	
	public ModelSettingsButton() {
		super(R.drawable.button_settings, new BoundsCircle(40), null, null, false, false, false, null);
	}
	
	@Override
	public int depth() {
		return 100;
	}
}