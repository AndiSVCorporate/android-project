package android.project.models;

import android.project.Object2D;
import android.project.R;
import android.project.Scheduler.Place;

public class RandomOneJumpFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	public RandomOneJumpFallingObject(Place p, long tf, float floor, Object2D w) {
		super(R.drawable.colorbird, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
		_only=p;
		
		scale();
		addRotation();
	}
	@Override
	public void jump() {
		int x=0;
		switch(_only){
		case LEFT: x=200; break;
		case MIDDLE: x=400; break;
		case RIGHT: x=600; break;
		}		
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, x, 430 - _floor, _tFall));
		}
		else{
			ModelJumpingObject jmp = (ModelJumpingObject) _ball.getParent();
			jmp.finalizePosition();
			jmp.freeInnerObject(_ball);
			_world.removeObject(jmp);
			_world.addObject(new ModelJumpingObject(_ball, jmp.getTFall(), 400, 430 - _floor, jmp.getTime() - 2 * jmp.getTFall()));				
		}
		++_jump;
	}
	@Override
	public boolean jobDone() {
		return _jump==2;
	}
	@Override
	public int getScore() {
		return 500;
	}
}
