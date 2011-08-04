package android.project;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;

public class Utils {
	
	
	private static BitmapManager _bitmapManager = null;
	
	/* Global Matrixes for fast point calculation. */
	
	private static Matrix _canvasCalibrationMatrix = new Matrix();
	private static Matrix _inverseCanvasCalibrationMatrix = new Matrix();
	private static Matrix _canvasBaseMatrix = new Matrix();
	private static Matrix _inverseCanvasBaseMatrix = new Matrix();
	
	private static long _timeDiff = 0;
	
	private static Typeface _typeface;
	
	private static CanvasRenderer _canvasRenderer;
	
	private static Canvas _canvas;
	
	/* Global getters and setters to avoid passing endless arguments. */
	
	public static void setBitmapManager(BitmapManager bitmapManager) {
		_bitmapManager = bitmapManager;
	}
	
	public static BitmapManager getBitmapManager() {
		return _bitmapManager;
	}
	
	public static void setCanvasRenderer(CanvasRenderer canvasRenderer) {
		_canvasRenderer = canvasRenderer;
	}
	
	public static CanvasRenderer getCanvasRenderer() {
		return _canvasRenderer;
	}
	
	public static void setCanvas(Canvas canvas) {
		_canvas = canvas;
	}
	
	public static Canvas getCanvas() {
		return _canvas;
	}
	
	public static void setTypeface(Typeface typeface) {
		_typeface = typeface;
	}
	
	public static Typeface getTypeface() {
		return _typeface;
	}
	
	public static void setTimeDiff(long timeDiff) {
		_timeDiff = timeDiff;
	}
	
	public static long getTimeDiff() {
		return _timeDiff;
	}
	
	public static void setCanvasBaseMatrix(Matrix canvasBaseMatrix) {
		_canvasBaseMatrix = canvasBaseMatrix;
		_canvasBaseMatrix.invert(_inverseCanvasBaseMatrix);
	}
		
	public static Matrix getCanvasBaseMatrix() {
		return _canvasBaseMatrix;
	}
	
	public static Matrix getInverseCanvasBaseMatrix() {
		return _inverseCanvasBaseMatrix;
	}
	
	public static void setCanvasCalibrationMatrix(Matrix canvasCalibrationMatrix) {
		_canvasCalibrationMatrix = canvasCalibrationMatrix;
		_canvasCalibrationMatrix.invert(_inverseCanvasCalibrationMatrix);
	}
	
	public static Matrix getInverseCanvasCalibrationMatrix() {
		return _inverseCanvasCalibrationMatrix;
	}
		
	public static Matrix getCanvasCalibrationMatrix() {
		return _canvasCalibrationMatrix;
	}
	
	public static void postInvalidate(float left, float top, float right, float bottom) {
		
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
			if (a - Constants.MATH_EPSILON > b)
				return 1;
			else
				return 0;
		else
			if (b - Constants.MATH_EPSILON > a)
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
	 * calculating the transformation matrix.
	 * @param canvas the canvas.
	 * @param points the points to transform [x0 y0 x1 y1 ...].
	 */
	public static void mapPoints(Canvas canvas, float[] points) {
		Matrix transformationMatrix = getTransformationMatrix(canvas);
		transformationMatrix.mapPoints(points);
	}
	
	/**
	 * Returns the transformation matrix of the canvas.
	 * @param canvas the given canvas.
	 * @return transformation matrix of the canvas.
	 */
	public static Matrix getTransformationMatrix(Canvas canvas) {
		
		/* Denote B for canvas base matrix.
		 * Denote C for canvas calibration matrix.
		 * Denote T for canvas transformation matrix.
		 * Denote Cm for canvas matrix.
		 * 
		 * The following holds: Cm = B * C * T
		 * 
		 * So we return:
		 * 
		 * C ^ (-1) * B ^ (-1) * Cm =
		 * C ^ (-1) * B ^ (-1) * B * C * T = T 
		 */
		
		Matrix transformation = canvas.getMatrix();
		
		transformation.postConcat(_inverseCanvasBaseMatrix);
		transformation.postConcat(_inverseCanvasCalibrationMatrix);
		
		return transformation;
	}
	
	public static Matrix getBaseCalibrationMatrix() {
		
		Matrix baseCalibrationMatrix = new Matrix();
		

		baseCalibrationMatrix.preConcat(_canvasCalibrationMatrix);
		baseCalibrationMatrix.preConcat(_canvasBaseMatrix);
		
		return baseCalibrationMatrix;
	}
}
