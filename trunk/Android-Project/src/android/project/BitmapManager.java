package android.project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {

	private Bitmap _firemanBitmap;
	private CalibrationData _firemanCalibrationData;
	
	private Bitmap _trampolineBitmap;
	private CalibrationData _trampolineCalibrationData;
	
	public BitmapManager(Resources res) {
		
		_firemanBitmap = BitmapFactory.decodeResource(res, R.drawable.fireman2);
		_firemanCalibrationData = new CalibrationData(0.0f, 0.0f, 1f, 1f, 0.0f);
		
		_trampolineBitmap = BitmapFactory.decodeResource(res, R.drawable.trampoline);
		_trampolineCalibrationData = new CalibrationData(0.0f, 0.0f, 1f, 1f, 0.0f);
	}
	
	public Bitmap getBitmap(int bitmapCode) {
		switch (bitmapCode) {
		case R.drawable.fireman2:
			return _firemanBitmap;
		case R.drawable.trampoline:
			return _trampolineBitmap;
		default:
			return null;
		}
	}
	
	public CalibrationData getCalibrationData(int bitmapCode) {
		switch (bitmapCode) {
		case R.drawable.fireman2:
			return _firemanCalibrationData;
		case R.drawable.trampoline:
			return _trampolineCalibrationData;
		default:
			return null;
		}
	}
}
