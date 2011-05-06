package android.project;

public class World extends Object2DBitmap {

	protected World() {
		super(0,
				new BoundsRect(800, 480),
				null,
				new CalibrationData(400, 240, 1, 1, 0),
				false, false, false);
		
		_objects.add(new ModelPlayer());
	}

}
