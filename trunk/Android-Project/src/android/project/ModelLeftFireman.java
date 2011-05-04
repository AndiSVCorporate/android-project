package android.project;

public class ModelLeftFireman extends Object2DBitmap {

	protected ModelLeftFireman() {
		super(R.drawable.fireman,
				0.1f, 0.1f, 0.1f, 0.1f,
				new CalibrationData(0, 0, 1, 1, 0),
				new CalibrationData(100, 20, 1, 1, 0),
				false, true, true);
	}

}
