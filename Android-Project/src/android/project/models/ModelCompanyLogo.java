package android.project.models;

import android.project.Constants;
import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;

public class ModelCompanyLogo extends Object2DBitmap {
	
	public ModelCompanyLogo() {
		super(R.drawable.epicfailgamingstudios,
				null,
				null,
				new Position(Constants.ASPECT_WIDTH / 2, Constants.ASPECT_HEIGHT / 2, 1, 1, 0),
				false, false, false, null);
	}

}
