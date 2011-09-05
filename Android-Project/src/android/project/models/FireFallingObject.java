package android.project.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.R;
import android.project.Utils;
import android.project.bounds.BoundsCircle;
import android.util.Log;

public class FireFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	private boolean _onFire;
	private ModelSmoke _fire1;
	private ModelSmoke _fire2;
	private ModelSmoke _fire3;
	public FireFallingObject(long tf, float floor, Object2D w) {
		super(R.drawable.burnedbird, 75, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
		_onFire=true;
		_ball.setX(50);
		_fire1=new ModelSmoke(0, 0, 0);
		_fire2=new ModelSmoke(0, 0, 0);
		_fire3=new ModelSmoke(0, 0, 0);

		scale();
		addRotation();

		BoundsCircle b=new BoundsCircle(50);
		_ball.setBounds(b);
		
		_ball.addObject(_fire1);
		_ball.addObject(_fire2);
		_ball.addObject(_fire3);

	}
	@Override
	public void jump() {
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, 200, 430 - _floor, _tFall));
		}
		else{
			ModelJumpingObject jmp = (ModelJumpingObject) _ball.getParent();
			jmp.finalizePosition();
			jmp.freeInnerObject(_ball);
			_world.removeObject(jmp);
			_world.addObject(new ModelJumpingObject(_ball, jmp.getTFall(), 100, 430 - _floor, jmp.getTime() - 2 * jmp.getTFall()));				
		}
		++_jump;
	}
	@Override
	public boolean jobDone() {
		return _jump==4;
	}
	
	public boolean readyToJump(){
		return _onFire==false;
	}
	public void birdTouched(float x, float y){
		Log.d("touch","outside");
		if(_ball.isPointInside(x, y)){
			Log.d("touch","inside");
			_onFire=false;
			Utils.vibrate(50);
			_ball.removeObject(_fire1);
			_ball.removeObject(_fire2);
			_ball.removeObject(_fire3);
		}
	}

}
