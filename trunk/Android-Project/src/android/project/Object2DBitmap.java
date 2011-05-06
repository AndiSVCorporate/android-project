package android.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Object2DBitmap extends Object2D {

	private int _id;
	
	protected Object2DBitmap(int id,
			Bounds bounds,
			CalibrationData calibrationData, CalibrationData position,
			boolean isAbsolute, boolean drawCenter, boolean drawBorders) {
		super(bounds, calibrationData, position, isAbsolute, drawCenter, drawBorders);
		_id = id;
	}
	
	@Override
	protected void drawThis(Canvas c) {
		BitmapManager bitmapManager = Utils.getBitmapManager();
		Bitmap bitmap = bitmapManager.getBitmap(_id);
		CalibrationData calibrationData = bitmapManager.getCalibrationData(_id);
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
