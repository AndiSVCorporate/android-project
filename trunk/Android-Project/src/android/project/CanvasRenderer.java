package android.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class CanvasRenderer extends View {

	private World _world;

	private BitmapManager _bitmapManager;
	
	/* Privates for calibrating the screen to ASPECT_RATIO */

	private int _rWidth;
	private int _rHeight;
	private int _fWidth;
	private int _fHeight;
	private int _width;
	private int _height;
	private int _top;
	private int _left;
	private boolean _landscape;
	float _ratio;

	public CanvasRenderer(Context context, int rWidth, int rHeight) {
		super(context);

		calibrateScreen(rWidth, rHeight);

		_bitmapManager = new BitmapManager(getResources());

		Utils.setBitmapManager(_bitmapManager);
		Utils.setWidth(_width);
		Utils.setHeight(_height);
		
		_world = new World();
	}

	@Override
	public void onDraw(Canvas canvas) {
		

		if (_landscape) {
			canvas.rotate(90);
			canvas.translate(0, - _rWidth);
		}

		Paint paint = new Paint();
		
		paint.setColor(Color.WHITE);
		canvas.drawRect(_left, _top, _left + _width, _top + _height, paint);
		
		canvas.save();
		canvas.translate(_left, _top);
		
		_world.draw(canvas);
		
		canvas.restore();

		/* Draw clipping borders. */
		paint.setColor(Color.BLACK); // Border color.
		canvas.drawRect(0, 0, _left - 1, _fHeight, paint); // Left border. 
		canvas.drawRect(_left + _width + 1, 0, _fWidth, _fHeight, paint); // Right border.
		canvas.drawRect(0, 0, _fWidth, _top - 1, paint); // Top border. 
		canvas.drawRect(0, _top + _height + 1, _fWidth, _fHeight, paint); // Bottom border.
		
		
	}

	/* Privates */
	private void calibrateScreen(int rWidth, int rHeight) {
		
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
	}
	
}
