package android.project;

public class World extends Object2DBitmap {

	protected World() {
		super(0, 0.5f, 0.5f, 0.5f, 0.5f, null, null, true, true, false);
		
		_objects.add(new ModelLeftFireman());
		_objects.add(new ModelRightFireman());
	}

}
