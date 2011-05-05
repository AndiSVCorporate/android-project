package android.project;

public class Utils {
	
	private static BitmapManager _bitmapManager = null;
	
	public static void setBitmapManager(BitmapManager bitmapManager) {
		_bitmapManager = bitmapManager;
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
