package android.project.models;

import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;

public class VaultFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	public VaultFallingObject(long tf, float floor, Object2D w) {
		super(R.drawable.vault2, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
	}
	@Override
	public void jump() {
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, 200, 420 - _floor, _tFall));
		}
		else{
			ModelJumpingObject jmp = (ModelJumpingObject) _ball.getParent();
			jmp.finalizePosition();
			jmp.freeInnerObject(_ball);
			_world.removeObject(jmp);
			_world.addObject(new ModelJumpingObject(_ball, jmp.getTFall(), 100, 420 - _floor, jmp.getTime() - 2 * jmp.getTFall()));				
		}
		++_jump;
	}
	@Override
	public boolean jobDone() {
		return _jump==4;
	}

	@Override
	public int getScore() {
		return 150;
	}
}
