package android.project.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.R;
import android.project.World;
import android.project.bounds.BoundsCircle;

public class FireFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	private boolean _onFire;
	private ModelSmoke _fire;
	protected FireFallingObject(long tf, float floor, Object2D w) {
		super(R.drawable.model_bird_1_falling, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setX(100);
		_ball.setY(_floor);
		_onFire=true;
		Paint p=new Paint();
		p.setColor(Color.RED);
		_ball=new ModelCircle(30, 100, _floor,p);
		BoundsCircle b=new BoundsCircle(30);
		_ball.setBounds(b);
		_fire=new ModelSmoke(0, 0);
		_ball.addObject(_fire);
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
	
	public boolean readyToJump(){
		return _onFire==false;
	}
	public void birdTouched(float x, float y){
		if(_ball.isPointInside(x, y)){
			_onFire=false;
			_ball.removeObject(_fire);
		}
	}

}
