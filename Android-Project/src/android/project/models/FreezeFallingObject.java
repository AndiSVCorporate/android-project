package android.project.models;

import android.project.Level;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;

public class FreezeFallingObject extends FallingObject {
	private int _jump;
	private float _floor;
	private static int FREEZED=0;
	
	public FreezeFallingObject(long tf, float floor, Object2D w) {
		super(R.drawable.icebird, 25, tf, w);
		_jump=0;
		_floor=floor;
		_ball.setY(_floor);
		if(FREEZED==0){
			((ModelPlayScreen)w).filterIn();
		}
		FREEZED++;

		scale();
		addRotation();

	}
	@Override
	public void jump() {
		if(_jump==0){
			_world.addObject(new ModelJumpingObject(_ball, _tFall, 200, 430 - _floor, _tFall));
			ModelJumpingObject.setSPEED_RATIO((float) 0.5);
			ModelRotateObject.set_speedRatio((float) 0.5);
			Level.setSPEED_RATIO((float)0.5);
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
	@Override
	public void crash() {
		super.crash();
		FREEZED--;
		if(FREEZED==0){
			ModelJumpingObject.setSPEED_RATIO((float) 1);
			ModelRotateObject.set_speedRatio((float) 1);		
			Level.setSPEED_RATIO(1);
			((ModelPlayScreen)_world).filterOut();
		}
	}
	
	public static void initialize(){
		FREEZED=0;
	}
}
