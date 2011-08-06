package android.project.models;

import android.project.Object2DBitmap;
import android.project.R;

public class ModelQuitButton extends Object2DBitmap {
	
	public ModelQuitButton() {
		super(R.drawable.button_quit, null, null, null, false, false, false, null);
	}
	
	@Override
	public int depth() {
		return 100;
	}
}