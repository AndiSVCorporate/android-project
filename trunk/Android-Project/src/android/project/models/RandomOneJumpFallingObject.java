package android.project.models;

import java.util.Random;

import android.project.Object2D;
import android.project.R;
import android.project.Scheduler.Place;
import android.project.World;

public class RandomOneJumpFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	public RandomOneJumpFallingObject(Place p, long tf, float floor, Object2D w) {
		super(R.drawable.model_bird_2_falling, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
		int r=0;
		switch(p){
		case LEFT: r=0; break;
		case MIDDLE: r=1; break;
		case RIGHT: r=2; break;
		}
//		_tFall=(1+2*r)*_tFall;
		_only=p;
	}
	@Override
	public void jump() {
		int x=0;
		switch(_only){
		case LEFT: x=200; break;
		case MIDDLE: x=350; break;
		case RIGHT: x=550; break;
		}		
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, x, 420 - _floor, _tFall));
		}
		else{
			ModelJumpingObject jmp = (ModelJumpingObject) _ball.getParent();
			jmp.finalizePosition();
			jmp.freeInnerObject(_ball);
			_world.removeObject(jmp);
			_world.addObject(new ModelJumpingObject(_ball, jmp.getTFall(), 400, 420 - _floor, jmp.getTime() - 2 * jmp.getTFall()));				
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
