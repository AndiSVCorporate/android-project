package android.project.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.R;
import android.project.World;
import android.project.Scheduler.Place;
import android.project.bounds.BoundsCircle;

public class PoisonFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	public PoisonFallingObject(Place not, long tf, float floor, Object2D w) {
		super(R.drawable.model_bird_2_falling, 50, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
		Paint p=new Paint();
		p.setColor(Color.BLACK);
		_ball=new ModelCircle(30, 100, _floor,p);
		BoundsCircle b=new BoundsCircle(30);
		_ball.setBounds(b);
		_only=not;
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
			((ModelPlayScreen)_world).subLife();
		}
		++_jump;
	}

	@Override
	public boolean jobDone() {
		return _jump==2;
	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public void crash() {
		super.crash();
		if(_ball.getX()<800)
			((ModelPlayScreen)_world).addLife();
	}

}