package android.project.models;

import android.project.bounds.BoundsRect;
import android.project.Constants;
import android.project.Object2DBitmap;
import android.project.Position;
import android.project.R;
import android.project.Utils;

public class ModelPlayer extends Object2DBitmap {
	
	private float _playerDestination;
	private long _timeFromSuccess;
	public ModelPlayer() {
		super(R.drawable.player2,
				new BoundsRect(Constants.SCREEN_PLAYER_WIDTH, Constants.SCREEN_PLAYER_HEIGHT),
				null,
				new Position(Constants.SCREEN_PLAYER_MIDDLE_X, Constants.SCREEN_PLAYER_MIDDLE_Y, 1, 1, 0),
				false, false, false, null);
		_playerDestination = Constants.SCREEN_PLAYER_MIDDLE_X;
		_timeFromSuccess=1000;
	}
	
	public void calculateThis(long timeDiff) {
		//Log.d("WTTTTTTTTTTF", "WHDSKHDKASHDKJADHKD");
		if(_timeFromSuccess<100){
			setBitmap(R.drawable.player2catch);
			_timeFromSuccess+=timeDiff;
		}
		else
			setBitmap(R.drawable.player2);
		
		float x = getRealX();

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
	
	public void setDestination(float x) {
		_playerDestination = x;
	}
	
	public void move(float clickX, float clickY) {
		if (clickY < 240)
			return;
		float x = getRealX();
		if (x + (Constants.SCREEN_PLAYER_WIDTH / 2) < clickX)
			if (Utils.floatCompare(_playerDestination, Constants.SCREEN_PLAYER_RIGHT_X) < 0)
				//if (Utils.floatCompare(_playerDestination, x) <= 0)
					_playerDestination = _playerDestination + Constants.SCREEN_PLAYER_MOVE_TOTAL_X;
		if (x - (Constants.SCREEN_PLAYER_WIDTH / 2) > clickX)
			if (Utils.floatCompare(_playerDestination, Constants.SCREEN_PLAYER_LEFT_X) > 0)
				//if (Utils.floatCompare(_playerDestination, x) >= 0)
					_playerDestination = _playerDestination - Constants.SCREEN_PLAYER_MOVE_TOTAL_X;		
	}
	
	@Override
	public int depth() {
		return Constants.DEPTH_PLAYER;
	}
	
	public void goodJob(){
		_timeFromSuccess=0;
	}
	
}
