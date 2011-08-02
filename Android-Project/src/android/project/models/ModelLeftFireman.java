package android.project.models;

import android.project.BoundsRect;
import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;

public class ModelLeftFireman extends Object2DBitmap {

	protected ModelLeftFireman() {
		super(R.drawable.fireman2,
				new BoundsRect(33, 61),
				new Positioning(- 16, - 31, 1, 1, 0),
				new Positioning(- 60, 0, 1, 1, 0),
				false, false, false, null);
	}

}
