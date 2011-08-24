package android.project.models;

import android.project.Object2D;
import android.project.R;
import android.project.World;

public class OneJumpFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	protected OneJumpFallingObject(long tf, float floor, Object2D w) {
		super(R.drawable.flag_bird, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setX(100);
		_ball.setY(_floor);
	}
	@Override
	public void jump() {
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, 350, 440 - _floor, _tFall));
		}
		else{
			ModelJumpingObject jmp = (ModelJumpingObject) _ball.getParent();
			jmp.finalizePosition();
			jmp.freeInnerObject(_ball);
			_world.removeObject(jmp);
			_world.addObject(new ModelJumpingObject(_ball, jmp.getTFall(), 350, 440 - _floor, jmp.getTime() - 2 * jmp.getTFall()));				
		}
		++_jump;
	}
	@Override
	public boolean jobDone() {
		return _jump==2;
	}

}
