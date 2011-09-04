package android.project.models;

import java.util.Random;

import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Scheduler.Place;

public abstract class FallingObject {
	protected Object2D _ball;
	protected int _score;
	protected long _tFall;
	protected Object2D _world;
	protected Place _only;
	protected FallingObject(int draw, int score, long tf, Object2D w){
		_ball= new Object2DBitmap(draw);
		_ball.setX(50);
		_score=score;
		_tFall=tf;
		_world=w;
		_only=Place.NONE;
	}
	public int getScore(){
		return _score;
	}
	public abstract void jump();
	public void crash(){
		if(getRealX()<800)
			((ModelPlayScreen)_world).subLife();
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
	public Place getPlace(){
		return _only;
	}
	public void addRotation(){
		_ball=new ModelRotateObject(_ball, 0, 7200, 100000);		
	}
	
	public void scale(){
		_ball=new ModelScaleObject(_ball, (float) 0.25, 1, 100);		
	}
}