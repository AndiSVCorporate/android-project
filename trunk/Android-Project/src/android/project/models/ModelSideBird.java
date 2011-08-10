package android.project.models;

import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;

public class ModelSideBird extends Object2DBitmap {
	
	public ModelSideBird(float x, float y) {
		super(R.drawable.side_bird, null, null, new Position(x, y),
				false, false, false, null);
	}

}