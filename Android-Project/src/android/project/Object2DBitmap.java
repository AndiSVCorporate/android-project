package android.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Object2DBitmap extends Object2D {

	private int _bitmapId;

	public Object2DBitmap(int id) {
		this(id, null, null, null, false, false, false, null);
	}
	
	public Object2DBitmap(int id,
			Bounds bounds,
			Position calibrationData, Position position,
			boolean isAbsolute, boolean drawCenter, boolean drawBorders, Object2D parent) {
		super(bounds, calibrationData, position, isAbsolute, drawCenter, drawBorders, parent);
		_bitmapId = id;
	}

	@Override
	public void drawThis(Canvas c) {
		BitmapManager bitmapManager = Utils.getBitmapManager();
		Bitmap bitmap = bitmapManager.getBitmap(_bitmapId);
		Position calibrationData = bitmapManager.getCalibrationData(_bitmapId);
		if (bitmap == null)
			return;

		if (calibrationData != null) {
			c.save();
			c.translate(calibrationData.getX(), calibrationData.getY());
			c.scale(calibrationData.getScaleX(), calibrationData.getScaleY());
			c.rotate(calibrationData.getAngle());
		}
		
		//long start = System.nanoTime();
		c.drawBitmap(bitmap, 0.0f, 0.0f, null);
		//long end = System.nanoTime();
		//Log.d("draw", this.getClass().getName() + " " + (float) ((end - start) / 1000000));
		if (calibrationData != null)
			c.restore();
	}

	public void setBitmap(int bitmapId) {
		_bitmapId = bitmapId;
	}

	public int getBitmap() {
		return _bitmapId;
	}

}
