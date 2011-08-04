package android.project.models;

import android.project.BoundsRect;
import android.project.Constants;
import android.project.Object2DBitmap;
import android.project.Positioning;
import android.project.R;
import android.project.Utils;
import android.util.Log;

public class ModelPlayer extends Object2DBitmap {
	
	private float _playerDestination;
	
	public ModelPlayer() {
		super(R.drawable.player2,
				new BoundsRect(Constants.SCREEN_PLAYER_WIDTH, Constants.SCREEN_PLAYER_HEIGHT),
				null,
				new Positioning(Constants.SCREEN_PLAYER_MIDDLE_X, Constants.SCREEN_PLAYER_MIDDLE_Y, 1, 1, 0),
				false, false, false, null);
		_playerDestination = Constants.SCREEN_PLAYER_MIDDLE_X;
	}
	
	public void calculateThis(long timeDiff) {
		//Log.d("WTTTTTTTTTTF", "WHDSKHDKASHDKJADHKD");
		
		setBitmap(R.drawable.player2);
		
		float x = getX();

		int dir = Utils.floatCompare(_playerDestination, x);
				
		//Log.d("wtf", "x = " + x);
		//Log.d("wtf", "dest = " + _playerDestination);
		
		if (dir == 0)
			return;
		
		float move = Constants.SCREEN_PLAYER_SPEED_PPS * ((float) timeDiff / 1000);
		
		float nextX = x + dir * move;
		
		if (dir * nextX > dir * _playerDestination) {
			nextX = _playerDestination;
		}
		
		translateX(nextX - x);
		setBitmap(R.drawable.player2pu);
	}
	
	public void move(float clickX, float clickY) {
		if (clickY < 240)
			return;
		float x = getX();
		if (x + (Constants.SCREEN_PLAYER_WIDTH / 2) < clickX)
			if (Utils.floatCompare(x, Constants.SCREEN_PLAYER_RIGHT_X) < 0)
				if (Utils.floatCompare(_playerDestination, x) <= 0)
					_playerDestination = _playerDestination + Constants.SCREEN_PLAYER_MOVE_TOTAL_X;
		if (x - (Constants.SCREEN_PLAYER_WIDTH / 2) > clickX)
			if (Utils.floatCompare(x, Constants.SCREEN_PLAYER_LEFT_X) > 0)
				if (Utils.floatCompare(_playerDestination, x) >= 0)
					_playerDestination = _playerDestination - Constants.SCREEN_PLAYER_MOVE_TOTAL_X;		
	}
	
	@Override
	public int depth() {
		return Constants.DEPTH_PLAYER;
	}
	
}
