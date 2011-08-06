package android.project.models;

import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;

public class ModelSideBird extends Object2DBitmap {
	
	public ModelSideBird() {
		super(R.drawable.side_bird, null, new Positioning(800, 480, 1, 1, 0),
				null, true, false, false, null);
	}

}