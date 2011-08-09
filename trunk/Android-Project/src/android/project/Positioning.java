package android.project;

/**
 * A container class of data needed for 2D calibration.
 */
public class Positioning {

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
	public Positioning(float calibrationX, float calibrationY,
			float calibrationScaleX, float calibrationScaleY,
			float calibrationAngle) {
		_calibrationX = calibrationX;
		_calibrationY = calibrationY;
		_calibrationScaleX = calibrationScaleX;
		_calibrationScaleY = calibrationScaleY;
		_calibrationAngle = calibrationAngle;
	}

	public Positioning(Positioning other) {
		if (other == null) {
			_calibrationX = 0;
			_calibrationY = 0;
			_calibrationScaleX = 1;
			_calibrationScaleY = 1;
			_calibrationAngle = 0;
		} else {
			_calibrationX = other.getX();
			_calibrationY = other.getY();
			_calibrationScaleX = other.getCalibrationScaleX();
			_calibrationScaleY = other.getCalibrationScaleY();
			_calibrationAngle = other.getCalibrationAngle();
		}
	}

	public Positioning() {
		this(0, 0, 1, 1, 0);
	}
	
	public Positioning(float x, float y) {
		this(x, y, 1, 1, 0);
	}

	/**
	 * @return Returns X - axis translation value.
	 */
	public float getX() {
		return _calibrationX;
	}

	/**
	 * Sets X - axis translation value.
	 * @param calibrationX X - axis translation value.
	 */
	public void setX(float calibrationX) {
		_calibrationX = calibrationX;
	}

	/**
	 * @return Returns Y - axis translation value.
	 */
	public float getY() {
		return _calibrationY;
	}

	/**
	 * Sets Y - axis translation value.
	 * @param calibrationY Y - axis translation value.
	 */
	public void setY(float calibrationY) {
		_calibrationY = calibrationY;
	}

	/**
	 * @return Returns scaling value X - Axis.
	 */
	public float getCalibrationScaleX() {
		return _calibrationScaleX;
	}

	/**
	 * Sets X - axis scaling value.
	 * @param calibrationX X - axis scaling value.
	 */
	public void setCalibrationScaleX(float calibrationScaleX) {
		_calibrationScaleX = calibrationScaleX;
	}

	/**
	 * @return Returns scaling value Y - axis.
	 */
	public float getCalibrationScaleY() {
		return _calibrationScaleY;
	}

	/**
	 * Sets Y - axis scaling value.
	 * @param calibrationY Y - axis scaling value.
	 */
	public void setCalibrationScaleY(float calibrationScaleY) {
		_calibrationScaleY = calibrationScaleY;
	}

	/**
	 * @return Returns rotation value.
	 */
	public float getCalibrationAngle() {
		return _calibrationAngle;
	}

	/**
	 * Sets X - axis scaling value.
	 * @param calibrationX X - axis scaling value.
	 */
	public void setCalibrationAngle(float calibrationAngle) {
		_calibrationAngle = calibrationAngle;
	}

}
