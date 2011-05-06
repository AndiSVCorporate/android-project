package android.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasRenderer extends View {

	private World _world;

	private BitmapManager _bitmapManager;
	
	/* Privates for calibrating the screen to ASPECT_RATIO. */

	private int _rWidth;
	private int _rHeight;
	private int _fWidth;
	private int _fHeight;
	private int _width;
	private int _height;
	private int _top;
	private int _left;
	private boolean _landscape;
	private float _ratio;
	
	private Matrix _canvasCalibrationMatrix;
	private Matrix _canvasBaseMatrix;
	
	/* Privates for checking the frame rate. */
	
	private float _frameRate;
	private long _frames;
	private long _startTime;
	private long _time;

	public CanvasRenderer(Context context, int rWidth, int rHeight) {
		super(context);
		
		_canvasBaseMatrix = new Matrix();
		_canvasCalibrationMatrix = new Matrix();
		
		calculateScreen(rWidth, rHeight);

		_bitmapManager = new BitmapManager(getResources());
		
		Utils.setBitmapManager(_bitmapManager);
		Utils.setCanvasCalibrationMatrix(_canvasCalibrationMatrix);
		
		_world = new World();
		
		_time = 0;
		_frames = 0;
		_startTime = 0;
		_frameRate = 0;
	}

	@Override
	public void onDraw(Canvas canvas) {
		drawScreen(canvas);
		calculateFrameRate();
		postInvalidate();
	}

	/* Privates */
	
	private void calculateScreen(int rWidth, int rHeight) {
		
		_rWidth = rWidth;
		_rHeight = rHeight;

		if (_rWidth < _rHeight)
			_landscape = true;
		else
			_landscape = false;

		if (_landscape) {
			_fWidth = _rHeight;
			_fHeight = _rWidth;
		} else {
			_fWidth = _rWidth;
			_fHeight = _rHeight;
		}

		_ratio = (float) _fWidth / _fHeight;

		int cmpr = Utils.floatCompare(_ratio, Constants.ASPECT_RATIO);

		if (cmpr > 0) {
			_width = Utils.floatRound((float) _fHeight * Constants.ASPECT_RATIO);
			_height = _fHeight;
		} else {
			_width = _fWidth;
			_height = Utils.floatRound((float) _fWidth / Constants.ASPECT_RATIO);
		}		

		_top = (_fHeight - _height) / 2;
		_left = (_fWidth - _width) / 2;

		Log.d("CANVAS", "rWidth = " + _rWidth + "; rHeight = " + _rHeight);
		Log.d("CANVAS", "fWidth = " + _fWidth + "; fHeight = " + _fHeight);
		Log.d("CANVAS", "landscape = " + _landscape + "; width = " + _width + "; height = " + _height);
		Log.d("CANVAS", "left = " + _left + "; top = " + _top);
		
		if (_landscape) {
			_canvasCalibrationMatrix.preRotate(90);
			_canvasCalibrationMatrix.preTranslate(0, -_rWidth);
		}
		
		_canvasCalibrationMatrix.preTranslate(_left, _top);
		_canvasCalibrationMatrix.preScale(_width / Constants.ASPECT_WIDTH, _height / Constants.ASPECT_HEIGHT);
	}
	
	private void calculateFrameRate() {
		if (!Constants.CALCULATE_FRAME_RATE)
			return;
		if (_startTime == 0) {
			_startTime = System.currentTimeMillis();
			return;
		}
		_time = System.currentTimeMillis();
		_frames++;
		if (_time > _startTime + 1000) {
			_frameRate = _frames / ((float) (_time - _startTime) / 1000);
			Log.d("FRAME_RATE", "frameRate = " + _frameRate + "; frames = " + _frames);
			_startTime = _time;
			_frames = 0;
		}
	}
	
	private void drawScreen(Canvas canvas) {
		Paint paint = new Paint();
		
		canvas.getMatrix(_canvasBaseMatrix);
		
		Utils.setCanvasBaseMatrix(_canvasBaseMatrix);
		
		canvas.concat(_canvasCalibrationMatrix);
		
		paint.setColor(Color.WHITE);
		canvas.drawRect(0, 0, 800, 480, paint);
		
		_world.draw(canvas);

		/* Draw clipping borders. */
		paint.setColor(Color.BLACK); // Border color.
		if (_left > 0) {
			canvas.drawRect(- 1, 0, - 400, 480, paint); // Left border. 
			canvas.drawRect(801, 0, 1200, 480, paint); // Right border.
		}
		if (_top > 0) {
			canvas.drawRect(0, - 1, 800, - 241, paint); // Top border. 
			canvas.drawRect(0, 481, 800, 721, paint); // Bottom border.
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float[] point = new float[2];
		point[0] = event.getRawX();
		point[1] = event.getRawY();
		
		Matrix inverse = new Matrix();
		_canvasCalibrationMatrix.invert(inverse);
		Log.d("TOUCH", "rawX = " + point[0] + "; rawY = " + point[1]);
		inverse.mapPoints(point);
		Log.d("TOUCH", "x = " + point[0] + "; y = " + point[1]);
		//event.
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
