package android.project;

import android.graphics.Canvas;
import android.graphics.Matrix;

public class Utils {
	
	private static BitmapManager _bitmapManager = null;
	
	private static Matrix _canvasCalibrationMatrix = new Matrix();
	private static Matrix _canvasBaseMatrix = new Matrix();
	
	/* Global getters and setters to avoid passing endless arguments. */
	
	public static void setBitmapManager(BitmapManager bitmapManager) {
		_bitmapManager = bitmapManager;
	}
		
	public static BitmapManager getBitmapManager() {
		return _bitmapManager;
	}
	
	public static void setCanvasBaseMatrix(Matrix canvasBaseMatrix) {
		_canvasBaseMatrix = canvasBaseMatrix;
	}
		
	public static Matrix getCanvasBaseMatrix() {
		return _canvasBaseMatrix;
	}
	
	public static void setCanvasCalibrationMatrix(Matrix canvasCalibrationMatrix) {
		_canvasCalibrationMatrix = canvasCalibrationMatrix;
	}
		
	public static Matrix getCanvasCalibrationMatrix() {
		return _canvasCalibrationMatrix;
	}

	
	/* Float management helping functions. */
	
	/**
	 * Compares two floats.
	 * @param a first float.
	 * @param b second float.
	 * @return 0 if equal, 1 if a > b, -1 otherwise.
	 */
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
	
	/**
	 * Rounds a float number.
	 * @param a the float number.
	 * @return the rounded number.
	 */
	public static int floatRound(float a) {
		int ra = (int) a;
		if (a - (float) ra >= 0.5)
			return ra + 1;
		return ra;
	}
	
	/* Canvas management helping functions. */
	
	/**
	 * Returns the destination of the given point in the canvas by
	 * calculating the inverse transformation matrix.
	 * @param canvas the canvas.
	 * @param points the points to transform [x0 y0 x1 y1 ...].
	 */
	public void mapPoints(Canvas canvas, float[] points) {
		Matrix inverseTransformationMatrix = getInverseTransformationMatrix(canvas);
		inverseTransformationMatrix.mapPoints(points);
	}
	
	/**
	 * Returns the inverse transformation matrix of the canvas.
	 * @param canvas the given canvas.
	 * @return inverse transformation matrix of the canvas.
	 */
	public Matrix getInverseTransformationMatrix(Canvas canvas) {
		
		/* Denote B for canvas base matrix.
		 * Denote C for canvas calibration matrix.
		 * Denote T for canvas transformation matrix.
		 * Denote Cm for canvas matrix.
		 * 
		 * The following hold: Cm = T * C * B
		 * 
		 * So we return:
		 * 
		 * C * B * Cm ^ (-1) = C * B * B ^ (-1) * C ^ (-1) * T ^ (-1) = T ^ (-1). 
		 */
		
		Matrix inverseTransformationMatrix = new Matrix();
		
		canvas.getMatrix().invert(inverseTransformationMatrix);
		
		inverseTransformationMatrix.preConcat(_canvasBaseMatrix);
		inverseTransformationMatrix.preConcat(_canvasCalibrationMatrix);
		
		return inverseTransformationMatrix;
	}
	
	public static Matrix getBaseCalibrationMatrix() {
		
		Matrix baseCalibrationMatrix = new Matrix();
		

		baseCalibrationMatrix.preConcat(_canvasCalibrationMatrix);
		baseCalibrationMatrix.preConcat(_canvasBaseMatrix);
		
		return baseCalibrationMatrix;
	}
}
