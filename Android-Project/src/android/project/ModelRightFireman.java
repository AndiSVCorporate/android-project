package android.project;

public class ModelRightFireman extends Object2DBitmap {

	protected ModelRightFireman() {
		super(R.drawable.fireman,
				0.1f, 0.1f, 0.1f, 0.1f,
				new CalibrationData(0, 0, 1, 1, 0),
				new CalibrationData(300, 20, -0.3f, 0.3f, 45),
				false, true, true);
	}

}