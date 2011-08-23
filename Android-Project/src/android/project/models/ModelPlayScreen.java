package android.project.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.project.CanvasRenderer;
import android.project.Constants;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Scheduler;
import android.project.Utils;
import android.project.World;
import android.project.screens.GameScreen;
import android.util.Log;
import android.view.MotionEvent;

public class ModelPlayScreen extends Object2D {

	private ModelPlayer _player;
	private Scheduler _scheduler;
	private List<FallingObject> _balls;
	private boolean _playing;
	private ModeLife _life;
	private ModelCurrentScore _score;
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
		_life=new ModeLife(3);
		_score=new ModelCurrentScore();
		addObject(_score);
		addObject(_life);
		_life.setX(500);
		_life.setY(50);
		_life.setDepth(10000);
		addObject(new ModelBackground(0xffffffff));
		addObject(_player);
		addObject(new Object2DBitmap(R.drawable.building) {{setDepth(-500);}});

		_balls = new ArrayList<FallingObject>();
		addObject(new ModelMoveObject(new ModelRect(800, 240, -800, 0, 0xff66ccff) {{ setDepth(-20000); }}, 800, 0, 1000));
		addObject(new ModelMoveObject(new ModelRect(800, 240, 800, 240, 0xff33cc66) {{ setDepth(-2000); }}, -800, 0, 1000));

	}

	public void hide() {
		_playing=false;
		//_scheduler=null;
		removeObject(_player);
		for(FallingObject bird : _balls){
			//			if(bird!=null && bird.getParent()!=null)
			//				bird.getParent().removeObject(bird);
		}
		_balls.clear();
	}

	public void pause() {

	}

	public void resume() {

	}

	@Override
	public void calculateThis(long timeDiff) {
		if (!_playing)
			return;
		if(!_life.isAlive())
			((GameScreen)getScreen()).endGame();
		_scheduler.calculate(timeDiff);
		
		if (_scheduler.getNext() != 0) {
			float v = _scheduler.getNext();
			long tFall = (long) (100 * 1000 / v);

			Random r=new Random();
			float temp=r.nextFloat();
			if(Utils.floatCompare(temp, (float) 0.33)<0)
				temp=Constants.SCREEN_FLOOR_THIRD;
			else if(Utils.floatCompare(temp, (float) 0.66)<0)
				temp=(Constants.SCREEN_FLOOR_SECOND);
			else
				temp=(Constants.SCREEN_FLOOR_FIRST);
			FallingObject f;
			int a=r.nextInt(2);
			switch(a){
				case 0: 
					f=new OneJumpFallingObject(tFall, temp, (World) getWorld());break;
				case 1: f=new BasicFallingObject(tFall, temp, (World) getWorld());break;
				default: f=null;
			}
			f.jump();
			_balls.add(f);
		}

		for(FallingObject ball:_balls){
			if(!ball.isFinished())
				continue;
			if(Math.abs(ball.getRealX() - _player.getRealX()) < 80){
				ball.jump();
				_score.addScore(ball.getScore());
			} else if(!ball.jobDone()){
				_life.fail();
				ball.crash();
			}
		}
	}
}