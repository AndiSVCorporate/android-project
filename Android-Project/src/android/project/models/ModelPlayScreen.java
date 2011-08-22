package android.project.models;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Scheduler;
import android.project.Utils;
import android.util.Log;
import android.view.MotionEvent;

public class ModelPlayScreen extends Object2D {

	private ModelPlayer _player;
	private Scheduler _scheduler;
	private List<Object2D> _balls;
	private boolean _playing;
	
	public ModelPlayScreen() {
		_playing = false;
	}
	
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
		_playing = true;
		_player = new ModelPlayer();
		_scheduler = new Scheduler();
		
		addObject(new ModelBackground(0xffffffff));
		addObject(_player);
		addObject(new Object2DBitmap(R.drawable.building) {{setDepth(-500);}});
		
		_balls = new ArrayList<Object2D>();
		for (int i = 0; i < 3; ++i)
			_player.addObject(new ModelSmoke(0, 0));
		addObject(new ModelMoveObject(new ModelRect(800, 240, -800, 0, 0xff66ccff) {{ setDepth(-20000); }}, 800, 0, 1000));
		addObject(new ModelMoveObject(new ModelRect(800, 240, 800, 240, 0xff33cc66) {{ setDepth(-2000); }}, -800, 0, 1000));
	}
	
	public void hide() {
		
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		if (!_playing)
			return;
		_scheduler.calculate(timeDiff);
		
		if (_scheduler.getNext() != 0) {
			float v = _scheduler.getNext();
			long tFall = (long) (100 * 1000 / v);
			Object2D ball = new Object2DBitmap(R.drawable.model_bird_1_falling);
			ball.setX(150);
			ball.setY(50);
			getWorld().addObject(new ModelJumpingObject(ball, tFall, 100, 440 - 50, tFall));
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
				getWorld().removeObject(jmp);
				getWorld().addObject(new ModelJumpingObject(ball, jmp.getTFall(), 100, 440 - 50, jmp.getTime() - 2 * jmp.getTFall()));				
			} else {
				jmp.freeInnerObject(ball);
				getWorld().removeObject(jmp);
			}
		}
	}

}
