package android.project.models;

import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;

public class ModelPlayButton extends Object2DBitmap {
	
	public ModelPlayButton(int x, int y) {
		super(R.drawable.button_play,
				null,
				null,
				new Positioning(x, y, 1, 1, 0),
				false, false, false, null);
	}
	
	@Override
	public int depth() {
		return 120;
	}
}
