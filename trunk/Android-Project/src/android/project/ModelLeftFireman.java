package android.project;

public class ModelLeftFireman extends Object2DBitmap {

	protected ModelLeftFireman(BitmapManager bitmapManager) {
		super(bitmapManager, R.drawable.fireman,
				new CalibrationData(0, 0, 1, 1, 0),
				new CalibrationData(100, 20, 1, 1, 0),
				true, true, false);
	}

}
