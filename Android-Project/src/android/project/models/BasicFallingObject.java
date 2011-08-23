package android.project.models;

import android.project.Object2D;
import android.project.R;
import android.project.World;

public class BasicFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	protected BasicFallingObject(long tf, float floor, World w) {
		super(R.drawable.model_bird_1_falling, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setX(100);
		_ball.setY(_floor);
	}
	@Override
	public void jump() {
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, 150, 440 - _floor, _tFall));
		}
		else{
			ModelJumpingObject jmp = (ModelJumpingObject) _ball.getParent();
			jmp.finalizePosition();
			jmp.freeInnerObject(_ball);
			_world.removeObject(jmp);
			_world.addObject(new ModelJumpingObject(_ball, jmp.getTFall(), 100, 440 - _floor, jmp.getTime() - 2 * jmp.getTFall()));				
		}
		++_jump;
	}
	@Override
	public boolean jobDone() {
		return _jump==4;
	}

}
