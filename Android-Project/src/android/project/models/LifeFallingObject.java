package android.project.models;

import android.project.Object2D;
import android.project.R;

public class LifeFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	public LifeFallingObject(long tf, float floor, Object2D w) {
		super(R.drawable.heart, 0, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
		
		scale();
		addRotation();
	}
	@Override
	public void jump() {
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, 200, 440 - _floor, _tFall));
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
//		if(_jump==4)
//			((GameScreen)_world.getScreen()).getPlay().addLife();
		return _jump==4;
	}
	
	@Override
	public void crash() {
		super.crash();
		((ModelPlayScreen)_world).addLife();
	}
}