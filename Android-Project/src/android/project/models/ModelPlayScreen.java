package android.project.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import android.project.CanvasRenderer;
import android.project.Constants;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Scheduler;
import android.project.Scheduler.Place;
import android.project.Utils;
import android.project.World;
import android.project.screens.GameScreen;
import android.util.Log;
import android.view.MotionEvent;

public class ModelPlayScreen extends Object2D {

	private ModelPlayer _player;
	private Scheduler _scheduler;
	private List<FallingObject> _balls;
	private ModeLife _life;
	private ModelCurrentScore _score;
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

		for(FallingObject ball : _balls){
			ball.birdTouched(x, y);
		}
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
		_life=new ModeLife(3);
		_score=new ModelCurrentScore();
		addObject(_score);
		addObject(_life);
		_life.setX(500);
		_life.setY(50);
		_life.setDepth(10000);
		addObject(new ModelBackground(0xffffffff));
		addObject(_player);
		_building = new Object2DBitmap(R.drawable.building);
		_building.setDepth(-500);
		_building.setX(-128);
		
		addObject(new ModelMoveObject(_building, 128, 0, 300));

		_balls = new ArrayList<FallingObject>();
		_sky = new ModelRect(800, 240, -800, 0, 0xff66ccff);
		_sky.setDepth(-20000);
		addObject(new ModelMoveObject(_sky, 800, 0, 1000));
		_ground = new ModelRect(800, 240, 800, 240, 0xff33cc66);
		_ground.setDepth(-2000);
		addObject(new ModelMoveObject(_ground, -800, 0, 1000));

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
		if(!_life.isAlive()){
			for(FallingObject b:_balls)
				b.crash();
			_balls.clear();
			((GameScreen)getScreen()).endGame();
		}
		_scheduler.calculate(timeDiff);
		Place p;
		Collection<FallingObject> toRemove=new ArrayList<FallingObject>(); 
		FallingObject f;
		Random r=new Random();
		int a=r.nextInt(3);
		switch(a){
			case 0: p=Place.MIDDLE;break;
			default: p=Place.NONE; break;
		}
		if (_scheduler.available(p)) {
			float v = _scheduler.getNext();
			long tFall = (long) (100 * 1000 / v);

			float temp=r.nextFloat();
			if(Utils.floatCompare(temp, (float) 0.33)<0)
				temp=Constants.SCREEN_FLOOR_THIRD;
			else if(Utils.floatCompare(temp, (float) 0.66)<0)
				temp=(Constants.SCREEN_FLOOR_SECOND);
			else
				temp=(Constants.SCREEN_FLOOR_FIRST);
			switch(a){
			case 0: {f=new OneJumpFallingObject(3*tFall, temp, this);break;}
			case 1: {f=new BasicFallingObject(tFall, temp, this);break;}
			case 2: {f=new FireFallingObject(tFall, Constants.SCREEN_FLOOR_THIRD, this);break;}
			default: f=null;
		}
			f.jump();
			_balls.add(f);
		}

		for(FallingObject ball:_balls){
			if(!ball.isFinished())
				continue;
			if(Math.abs(ball.getRealX() - _player.getRealX()) < 80 && ball.readyToJump()){
				ball.jump();
				_score.addScore(ball.getScore());
			} else if(!ball.jobDone()){
				_life.fail();
				ball.crash();
				toRemove.add(ball);
			}
			if(ball.getRealX()>800){
				ball.crash();
				toRemove.add(ball);
			}
		}
		_balls.removeAll(toRemove);
	}
	@Override
	public boolean isCalculateChildren() {
		return ((GameScreen)getScreen()).isPlaying();
	}

}