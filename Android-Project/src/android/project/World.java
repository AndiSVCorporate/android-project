package android.project;

public class World extends Object2DBitmap {

	protected World(BitmapManager bitmapManager) {
		super(bitmapManager, 0, null, null, true, true, false);
		_objects.add(new ModelLeftFireman(bitmapManager));
		_objects.add(new ModelRightFireman(bitmapManager));
	}

}
