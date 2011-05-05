package android.project;

public class World extends Object2DBitmap {

	protected World() {
		super(0, 240, 400, 240, 400,
				null,
				new CalibrationData(400, 240, 1, 1, 0),
				false, true, true);
		
		_objects.add(new ModelLeftFireman());
		_objects.add(new ModelRightFireman());
	}

}
