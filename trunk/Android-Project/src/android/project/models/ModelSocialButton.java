package android.project.models;

import android.project.Object2DBitmap;
import android.project.R;

public class ModelSocialButton extends Object2DBitmap {
	
	public ModelSocialButton() {
		super(R.drawable.button_social, null, null, null, false, false, false, null);
	}
	
	@Override
	public int depth() {
		return 100;
	}
}