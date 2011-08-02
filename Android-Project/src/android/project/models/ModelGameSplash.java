package android.project.models;

import android.project.Constants;
import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;

public class ModelGameSplash extends Object2DBitmap {
	
	public ModelGameSplash() {
		super(R.drawable.game_screen,
				null,
				null,
				new Positioning(Constants.ASPECT_WIDTH / 2, Constants.ASPECT_HEIGHT / 2, 1, 1, 0),
				false, false, false, null);
	}
	
	@Override
	public int depth() {
		return -100;
	}
}