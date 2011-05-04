package android.project;

public class Utils {
	
	private static int _width = 0;
	private static int _height = 0;
	
	private static BitmapManager _bitmapManager = null;
	
	public static void setWidth(int width) {
		_width = width;
	}
	
	public static void setHeight(int height) {
		_height = height;
	}
	
	public static void setBitmapManager(BitmapManager bitmapManager) {
		_bitmapManager = bitmapManager;
	}
	
	public static int getWidth() {
		return _width;
	}
	
	public static int getHeight() {
		return _height;
	}
	
	public static BitmapManager getBitmapManager() {
		return _bitmapManager;
	}

	public static int floatCompare(float a, float b) {
		if (a > b)
			if (a - Constants.EPSILON > b)
				return 1;
			else
				return 0;
		else
			if (b - Constants.EPSILON > a)
				return - 1;
			else
				return 0;
	}
	
	public static int floatRound(float a) {
		int ra = (int) a;
		if (a - (float) ra >= 0.5)
			return ra + 1;
		return ra;
	}
	
	public static float scaleTo(float original, float destination, float scale) {
		if (original == 0)
			return 0;
		return (scale / original) * destination;
	}
}
