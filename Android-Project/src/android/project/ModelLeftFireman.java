package android.project;

public class ModelLeftFireman extends Object2DBitmap {

	protected ModelLeftFireman() {
		super(R.drawable.fireman,
				0.08f, 0.03f, 0.08f, 0.03f,
				new CalibrationData(-15, -25, 1, 1, 0),
				new CalibrationData(360, 20, 1, 1, 0),
				false, true, true);
	}

}
