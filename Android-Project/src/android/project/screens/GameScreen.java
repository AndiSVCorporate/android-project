package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.Utils;
import android.project.models.ModelBackground;
import android.project.models.ModelPlayer;
import android.project.models.ModelSmoke;
import android.util.Log;
import android.view.MotionEvent;

public class GameScreen extends Screen {

	private ModelPlayer _player;
	
	public GameScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		_player = new ModelPlayer();
		
		getWorld().addObject(new ModelBackground(0xffcc6600));
		getWorld().addObject(_player);
		//getWorld().addObject(new ModelBezierCurve(Color.GREEN));
		
		for (int i = 0; i < 3; ++i)
			_player.addObject(new ModelSmoke(0, 0));
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		int actionIndex = event.getActionIndex();
		
		float[] point = new float[] {event.getX(actionIndex), event.getY(actionIndex)};
		
		//Log.d("click0", "x = " + point[0] + "; y = " + point[1]);
		Utils.getInverseCanvasCalibrationMatrix().mapPoints(point);
		
		float x = point[0];
		float y = point[1];
		
		//Log.d("player", "x = " + _player.getX() + "; y = " + _player.getY());
		
		Log.d("check", "index " + actionIndex);
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
			
			Log.d("click", "x = " + x + "; y = " + y);
			Log.d("count", event.getPointerCount() + "");
			_player.move(x, y);
		}
		return true;
	}

	@Override
	public int getBorderColor() {
		return Color.BLACK;
	}

	@Override
	public void calculateThis(long timeDiff) { }
}
