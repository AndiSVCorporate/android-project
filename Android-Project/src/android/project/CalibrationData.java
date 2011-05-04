package android.project;

/**
 * A container class of data needed for 2D calibration.
 */
public class CalibrationData {

	/** Calibration by X - axis translation. */
	private float _calibrationX;
	/** Calibration by Y - axis translation. */
	private float _calibrationY;

	/** Calibration by scaling X - axis. */
	private float _calibrationScaleX;
	/** Calibration by scaling Y - axis. */
	private float _calibrationScaleY;
	
	/** Calibration by rotation. */
	private float _calibrationAngle;
	
	/**
	 * Constructor for initializing all calibration data.
	 * @param calibrationX X - axis translation value.
	 * @param calibrationY Y - axis translation value.
	 * @param calibrationScaleX - Scaling value X - axis.
	 * @param calibrationScaleY - Scaling value Y - axis.
	 * @param calibrationAngle - Rotation value.
	 */
	public CalibrationData(float calibrationX, float calibrationY,
			float calibrationScaleX, float calibrationScaleY,
			float calibrationAngle) {
		_calibrationX = calibrationX;
		_calibrationY = calibrationY;
		_calibrationScaleX = calibrationScaleX;
		_calibrationScaleY = calibrationScaleY;
		_calibrationAngle = calibrationAngle;
	}
	
	/**
	 * @return Returns X - axis translation value.
	 */
	public float getCalibrationX() {
		return _calibrationX;
	}
	
	/**
	 * @return Returns Y - axis translation value.
	 */
	public float getCalibrationY() {
		return _calibrationY;
	}
	
	/**
	 * @return Returns scaling value X - Axis.
	 */
	public float getCalibrationScaleX() {
		return _calibrationScaleX;
	}
	
	/**
	 * @return Returns scaling value Y - axis.
	 */
	public float getCalibrationScaleY() {
		return _calibrationScaleY;
	}
	
	/**
	 * @return Returns rotation value.
	 */
	public float getCalibrationAngle() {
		return _calibrationAngle;
	}
	
}
