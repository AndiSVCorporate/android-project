package android.project;

public class Utils {

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
}
