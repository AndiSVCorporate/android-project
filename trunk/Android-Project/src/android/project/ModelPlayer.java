package android.project;

public class ModelPlayer extends Object2DBitmap {

	public ModelPlayer() {
		super(0,
				new BoundsRect(Constants.PLAYER_WIDTH, 66),
				null,
				new CalibrationData(0, 200, 1, 1, 0),
				false, false, true);
		_objects.add(new ModelLeftFireman());
		_objects.add(new ModelRightFireman());
		_objects.add(new ModelTrampoline());
	}
}
