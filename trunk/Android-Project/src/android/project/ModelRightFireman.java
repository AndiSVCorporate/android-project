package android.project;

public class ModelRightFireman extends Object2DBitmap {

	protected ModelRightFireman() {
		super(R.drawable.fireman,
				10f, 10f, 10f, 10f,
				new CalibrationData(-15, -25, 1, 1, 0),
				new CalibrationData(200, 20, -1, 1, 0),
				false, true, true);
	}

}