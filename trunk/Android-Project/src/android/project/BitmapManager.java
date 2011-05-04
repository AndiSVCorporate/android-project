package android.project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {

	private Bitmap _firemanBitmap;
	private CalibrationData _firemanCalibrationData;
	
	public BitmapManager(Resources res) {
		
		_firemanBitmap = BitmapFactory.decodeResource(res, R.drawable.fireman);
		_firemanCalibrationData = new CalibrationData(-13.0f, -20.0f, 1, 1, 0.0f);
	}
	
	public Bitmap getBitmap(int bitmapCode) {
		switch (bitmapCode) {
		case R.drawable.fireman:
			return _firemanBitmap;
		default:
			return null;
		}
	}
	
	public CalibrationData getCalibrationData(int bitmapCode) {
		switch (bitmapCode) {
		case R.drawable.fireman:
			return _firemanCalibrationData;
		default:
			return null;
		}
	}
}
