package android.project.models;

import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.World;

public abstract class FallingObject {
	protected Object2D _ball;
	private int _score;
	protected long _tFall;
	protected Object2D _world;
	protected FallingObject(int draw, int score, long tf, Object2D w){
		_ball= new Object2DBitmap(draw);
		_score=score;
		_tFall=tf;
		_world=w;
	}
	public int getScore(){
		return _score;
	}
	public abstract void jump();
	public void crash(){
		Object2D.freeInnerObject(_world,_ball);
	}
	public float getRealX(){
		return _ball.getRealX();
	}
	public boolean isFinished(){
		if(_ball.getParent()==null)
			return false;
		return ((ModelJumpingObject) _ball.getParent()).isFinished();
	}
	public abstract boolean jobDone();
	public boolean readyToJump(){
		return true;
	}
	public void birdTouched(float x, float y){}
}