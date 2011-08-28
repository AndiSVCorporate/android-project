package android.project.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.project.FireAchivement;
import android.project.Level;
import android.project.Level.Bird;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Utils;
import android.project.screens.GameScreen;
import android.util.Log;
import android.view.MotionEvent;

public class ModelPlayScreen extends Object2D {

	private ModelPlayer _player;
	private Vector<Level> _levels;
	private List<FallingObject> _balls;
	private ModeLife _life;
	private ModelCurrentScore _score;
	private Object2D _sky;
	private Object2D _ground;
	private Object2D _building;
	private ModelCurrentLevel _curLevel;
	private long _levelTime;
	private List<FireAchivement> _toAchieve;
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
		_player.setDepth(0);
		Map<Level.Bird,Integer> l1=new HashMap<Level.Bird, Integer>();
		l1.put(Bird.BASIC, 10);
		l1.put(Bird.ONE_JUMP, 3);
		
		generateLevels();
		generateAchievments();
		_levelTime=0;
		_life=new ModeLife(3);
		_score=new ModelCurrentScore(150);
		_curLevel=new ModelCurrentLevel();
		addObject(_score);
		addObject(_life);
		addObject(_curLevel);
		_life.setX(670);
		_life.setY(40);
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
			((GameScreen)getScreen()).GameOver();
		}
		if(_levels.get(_curLevel.getLevel()).LevelDone() && _curLevel.getLevel()<=_levels.size() && _balls.isEmpty()){
			_curLevel.levelUp();
			_levelTime=0;
		}
		_levelTime+=timeDiff;
		_levels.get(_curLevel.getLevel()).calculate(timeDiff);
		Collection<FallingObject> toRemove=new ArrayList<FallingObject>(); 
		FallingObject f=null;
		if(_levelTime>2000)
			f=_levels.get(_curLevel.getLevel()).getNext();
		if (f!=null) {
			f.jump();
			_balls.add(f);
		}

		for(FallingObject ball:_balls){
			if(!ball.isFinished())
				continue;
			if(Math.abs(ball.getRealX() - _player.getRealX()) < 80 && ball.readyToJump()){
				Utils.vibrate(50);
				ball.jump();
				_player.goodJob();
				_score.addScore(ball.getScore());
				addObject(new ModelScoreFly(ball.getScore(), _player.getRealX()));
			} else if(!ball.jobDone()){
				Utils.vibrate(300);
				_life.fail();
				ball.crash();
				toRemove.add(ball);
			}
			if(ball.getRealX()>=800){
				ball.crash();
				toRemove.add(ball);
			}
		}
		checkAchivement();
		_balls.removeAll(toRemove);
	}
	@Override
	public boolean isCalculateChildren() {
		return ((GameScreen)getScreen()).isPlaying();
	}
	
	public void stopGame(){
		_sky.setDepthRecursive(-1);
		_ground.setDepthRecursive(-1);
	}
	public int getScore(){
		return _score.getScore();
	}
	private void generateLevels(){
		_levels=new Vector<Level>();
		Bird[] b={Bird.BASIC,Bird.ONE_JUMP,Bird.FIRE,Bird.LIFE,Bird.RANDOM_ONE_JUMP, Bird.POISON};
		int[] l1={5,0,0,0,0,0};
		int[] l2={7,3,0,1,0,0};
		int[] l3={5,5,0,0,0,0};
		int[] l4={8,6,0,1,1,0};
		int[] l5={8,3,0,0,3,0};
		int[] l6={20,5,1,1,2,0};
		int[] l7={30,10,2,0,1,0};
		int[] l8={20,20,1,0,0,0};
		int[] l9={20,15,2,0,2,0};
		int[] l10={40,15,3,1,5,0};
		int[] l11={20,20,1,0,0,1};
		int[] l12={20,15,2,0,2,3};
		int[] l13={40,15,3,1,5,5};

		_levels.add(new Level(b,l1,5000,this));
		_levels.add(new Level(b,l2,2000,this));
		_levels.add(new Level(b,l3,1000,this));
		_levels.add(new Level(b,l4,500,this));
		_levels.add(new Level(b,l5,400,this));
		_levels.add(new Level(b,l6,200,this));
		_levels.add(new Level(b,l7,100,this));
		_levels.add(new Level(b,l8,50,this));
		_levels.add(new Level(b,l9,10,this));
		_levels.add(new Level(b,l10,0,this));
		_levels.add(new Level(b,l11,0,this));
		_levels.add(new Level(b,l12,0,this));
		_levels.add(new Level(b,l13,0,this));

	}
	public void addLife(){
		_life.addLife();
	}
	public void subLife(){
		_life.fail();
	}
	
	private void generateAchievments(){
		_toAchieve=new Vector<FireAchivement>();
		_toAchieve.add(new FireAchivement("1193712") {
			@Override
			public boolean checkAchievement() {
				if(_curLevel.getLevel()==6)
					reach();
				return isReached();
				
			}
		});
		_toAchieve.add(new FireAchivement("1194042") {
			@Override
			public boolean checkAchievement() {
				if(_score.getScore()>10000)
					reach();
				return isReached();
				
			}
		});
		
	}
	private void checkAchivement(){
		for(int i=0;i<_toAchieve.size();i++){
			if(_toAchieve.get(i).checkAchievement())
				_toAchieve.remove(i--);
		}
	}
}