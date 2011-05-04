package android.project;

public class ModelRightFireman extends Object2DBitmap {

	protected ModelRightFireman(BitmapManager bitmapManager) {
		super(bitmapManager, R.drawable.fireman,
				new CalibrationData(0, 0, 1, 1, 0),
				new CalibrationData(300, 20, -1, 1, 0),
				true, true, false);
	}

}