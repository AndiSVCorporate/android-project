package android.project;

public class ModelTrampoline extends Object2DBitmap {

	protected ModelTrampoline() {
		super(R.drawable.trampoline,
				new BoundsRect(89, 12),
				new CalibrationData(- 45, - 6, 1, 1, 0),
				new CalibrationData(0, 15, - 1, 1, 0),
				false, false, false);
	}
	
}
