package android.project.models;

import java.util.ArrayList;
import java.util.List;

import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Scheduler;
import android.project.Utils;
import android.project.screens.GameScreen;
import android.util.Log;
import android.view.MotionEvent;

public class ModelPlayScreen extends Object2D {

	private ModelPlayer _player;
	private Scheduler _scheduler;
	private List<Object2D> _balls;
	
	private Object2D _sky;
	private Object2D _ground;
	private Object2D _building;
	
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		int actionIndex = event.getActionIndex();
		
		float[] point = new float[] {event.getX(actionIndex), event.getY(actionIndex)};
		
		//Log.d("click0", "x = " + point[0] + "; y = " + point[1]);
		Utils.getInverseCanvasCalibrationMatrix().mapPoints(point);
		
		float x = point[0];
		float y = point[1];
		
		//Log.d("player", "x = " + _player.getX() + "; y = " + _player.getY());
		
		Log.d("check", "index " + actionIndex);
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
			
			Log.d("click", "x = " + x + "; y = " + y);
			Log.d("count", event.getPointerCount() + "");
			_player.move(x, y);
		}
		return true;
	}	
	
	public void onBackPressed() {
		
	}
	
	public void show() {
		_player = new ModelPlayer();
		_player.setX(1000);
		
		_scheduler = new Scheduler();
		
		addObject(_player);
		_building = new Object2DBitmap(R.drawable.building);
		_building.setDepth(-500);
		_building.setX(-128);
		
		addObject(new ModelMoveObject(_building, 128, 0, 300));
		
		_balls = new ArrayList<Object2D>();
		for (int i = 0; i < 3; ++i)
			_player.addObject(new ModelSmoke(0, 0));
		_sky = new ModelRect(800, 240, -800, 0, 0xff66ccff);
		_sky.setDepth(-20000);
		addObject(new ModelMoveObject(_sky, 800, 0, 1000));
		_ground = new ModelRect(800, 240, 800, 240, 0xff33cc66);
		_ground.setDepth(-2000);
		addObject(new ModelMoveObject(_ground, -800, 0, 1000));
	}
	
	public void stopGame() {
		_sky.setDepthRecursive(-1);
		_ground.setDepthRecursive(-1);
	}
	
	public void hide() {
		
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		if (!((GameScreen)getScreen()).isPlaying())
			return;
		_scheduler.calculate(timeDiff);
		
		if (_scheduler.getNext() != 0) {
			float v = _scheduler.getNext();
			long tFall = (long) (100 * 1000 / v);
			Object2D ball = new Object2DBitmap(R.drawable.model_bird_1_falling);
			ball.setX(150);
			ball.setY(50);
			addObject(new ModelJumpingObject(ball, tFall, 100, 440 - 50, tFall));
			_balls.add(ball);
		}
		
		for(Object2D ball:_balls){
			ModelJumpingObject jmp = (ModelJumpingObject) ball.getParent();
			if(jmp == null)
				continue;
			if(!jmp.isFinished())
				continue;
			if(Math.abs(ball.getRealX() - _player.getRealX()) < 80){
				jmp.finalizePosition();
				jmp.freeInnerObject(ball);
				removeObject(jmp);
				addObject(new ModelJumpingObject(ball, jmp.getTFall(), 100, 440 - 50, jmp.getTime() - 2 * jmp.getTFall()));				
			} else {
				jmp.freeInnerObject(ball);
				removeObject(jmp);
			}
		}
	}
	
	@Override
	public boolean isCalculateChildren() {
		return ((GameScreen)getScreen()).isPlaying();
	}

}
