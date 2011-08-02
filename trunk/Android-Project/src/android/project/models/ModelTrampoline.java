package android.project.models;

import android.project.BoundsRect;
import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;

public class ModelTrampoline extends Object2DBitmap {

	protected ModelTrampoline() {
		super(R.drawable.trampoline,
				new BoundsRect(89, 12),
				new Positioning(- 45, - 6, 1, 1, 0),
				new Positioning(0, 15, - 1, 1, 0),
				false, false, false, null);
	}
	
}
