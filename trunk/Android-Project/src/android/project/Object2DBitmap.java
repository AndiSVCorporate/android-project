package android.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Object2DBitmap extends Object2D {

	private int _id;
	private BitmapManager _bitmapManager;
	
	protected Object2DBitmap(BitmapManager bitmapManager, int id,
			CalibrationData calibrationData, CalibrationData position,
			boolean isAbsolute, boolean drawCenter, boolean drawBorders) {
		super(calibrationData, position, isAbsolute, drawCenter, drawBorders);
		_bitmapManager = bitmapManager;
		_id = id;
	}
	
	@Override
	protected void drawThis(Canvas c) {
		Bitmap bitmap = _bitmapManager.getBitmap(_id);
		CalibrationData calibrationData = _bitmapManager.getCalibrationData(_id);
		if (bitmap == null)
			return;
		if (calibrationData == null)
			return;
		
		c.save();
		c.translate(calibrationData.getCalibrationX(), calibrationData.getCalibrationY());
		c.scale(calibrationData.getCalibrationScaleX(), calibrationData.getCalibrationScaleY());
		c.rotate(calibrationData.getCalibrationAngle());
		
		c.drawBitmap(bitmap, 0.0f, 0.0f, null);
		
		c.restore();
	}

}
