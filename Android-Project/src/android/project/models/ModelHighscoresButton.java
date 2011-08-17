package android.project.models;

import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelHighscoresButton extends Object2DBitmap {
	
	public ModelHighscoresButton() {
		super(R.drawable.highscore, new BoundsCircle(40), null, null, false, false, false, null);
		setDepth(70);
	}
}