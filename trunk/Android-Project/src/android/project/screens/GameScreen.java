package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.Screen;
import android.project.Utils;
import android.project.models.ModelBackground;
import android.project.models.ModelGameSplash;
import android.project.models.ModelPlayer;
import android.util.Log;
import android.view.MotionEvent;

public class GameScreen extends Screen {

	private ModelPlayer _player;
	 private ModelGameSplash _logo;
	
	public GameScreen(CalculateThread calculateThread) {
		super(calculateThread);
		_player = new ModelPlayer();
		_logo = new ModelGameSplash();
		
		_world.addObject(new ModelBackground(Color.WHITE));
		_world.addObject(_player);
		//_world.addObject(_logo);
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
	public void postInvalidate() {
		//v.postInvalidate(_player.getX() - Constants.SCREEN_PLAYER_WIDTH / 2,
			//	_player.getY() - Constants.SCREEN_PLAYER_HEIGHT / 2,
				//_player.getX() + Constants.SCREEN_PLAYER_WIDTH / 2,
				//_player.getY() + Constants.SCREEN_PLAYER_HEIGHT / 2);
	}
}
