package android.project.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Utils;
import android.util.Log;

public class ModelGameMenuGameOver extends Object2D {
	
	private enum State {
		HIDDEN, SHOW, VISIBLE, HIDE 
	}
	
	private int _score;
	private State _state;
	private long _totalTime;
	
	//private Object2D _innerRect;
	//private Object2D _outerRect;
	//private ModelCircle _innerCircle;
	//private ModelCircle _outerCircle;
	private ModelText _gameOverText;
	private ModelText _gameOverTextStroke;
	
	private ModelText _lvlText;
	private ModelText _lvlTextStroke;
	
	private ModelText _scoreText;
	private ModelText _scoreTextStroke;
	
	private ModelText _bestScoreText;
	private ModelText _bestScoreTextStroke;
	
	private float _curWidth;
	private final static float MAX_WIDTH = 360;
	private final static long TIME_SLIDE = 300;
	private final static long TIME_TEXT = 300;
	
	public ModelGameMenuGameOver() {
		_state = State.HIDDEN;
	}
	
	public void show(int score, int level, int place) {
		if (_state != State.HIDDEN)
			return;
		Log.d("over", "show");
		_score = score;
		_state = State.SHOW;
		_curWidth = 0;
		_totalTime = 0;
		
		//_outerCircle = new ModelCircle(100, _curWidth, 0, 0xff006699);
		//_innerCircle = new ModelCircle(93, _curWidth, 0, 0xff00ccff);
		//_outerRect = new ModelRect(_curWidth, 200, 0, -100, 0xff006699);
		//_innerRect = new ModelRect(_curWidth, 186, 0, -93, 0xff00ccff);
		_gameOverText = new ModelText("GAME OVER", 0xff000000, 80);
		_gameOverText.setY(-170);
		_gameOverText.setX(-175);
		_gameOverTextStroke = new ModelText("GAME OVER", 0xffffffff, 80);
		_gameOverTextStroke.setY(-170);
		_gameOverTextStroke.setX(-175);
		_gameOverTextStroke.getPaint().setStyle(Paint.Style.STROKE);
		_gameOverTextStroke.getPaint().setStrokeWidth(4);
		
		//_lvlText = new ModelText("Lvl " + level, Color.GRAY, 30);
		//_lvlText.setY(-75);
		//_lvlText.setX(120);
		
		_scoreText = new ModelText("Score: " + score, Color.WHITE, 35);
		_scoreText.setY(-2);
		_scoreText.setX(120);
		
		_scoreTextStroke = new ModelText("Score: " + score, Color.BLACK, 35);
		_scoreTextStroke.setY(-2);
		_scoreTextStroke.setX(120);
		_scoreTextStroke.getPaint().setStyle(Paint.Style.STROKE);
		_scoreTextStroke.getPaint().setStrokeWidth(3);
		Log.d("over",""+Utils.getScores()[0]);
		_bestScoreText = new ModelText("Best:  " + Utils.getScores()[0].get_score(), 0xffffd737, 35);
		_bestScoreText.setY(37);
		_bestScoreText.setX(120);
		
		_bestScoreTextStroke = new ModelText("Best:  " + Utils.getScores()[0].get_score(), Color.BLACK, 35);
		_bestScoreTextStroke.setY(37);
		_bestScoreTextStroke.setX(120);
		_bestScoreTextStroke.getPaint().setStyle(Paint.Style.STROKE);
		_bestScoreTextStroke.getPaint().setStrokeWidth(3);
		
		//_outerCircle.setDepth(90);
		//_innerCircle.setDepth(91);
		//_outerRect.setDepth(92);
		//_innerRect.setDepth(93);
		_gameOverTextStroke.setDepth(120);
		_gameOverText.setDepth(120);
		_scoreText.setDepth(120);
		_scoreTextStroke.setDepth(120);
		_bestScoreText.setDepth(120);
		_bestScoreTextStroke.setDepth(120);
		//_lvlText.setDepth(120);
		//_outerCircle.getPaint().setAntiAlias(true);
		//_innerCircle.getPaint().setAntiAlias(true);
		
		//addObject(_innerCircle);
		//addObject(_outerCircle);
		//addObject(_innerRect);
		//addObject(_outerRect);
		//addObject(_lvlText);
		addObject(_gameOverText);
		addObject(_gameOverTextStroke);
		addObject(_scoreText);
		addObject(_scoreTextStroke);
		addObject(_bestScoreText);
		addObject(_bestScoreTextStroke);
	}
	
	public void hide() {
		if (_state != State.VISIBLE)
			return;
		_state = State.HIDE;
		_totalTime = 0;
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		if (_state == State.HIDDEN)
			return;
		if (_state == State.VISIBLE)
			return;
		_totalTime += timeDiff;
		if (_totalTime > TIME_SLIDE + TIME_TEXT) {
			_totalTime = TIME_SLIDE + TIME_TEXT;
		}
		if (_state == State.SHOW)
			showAnimation();
		if (_state == State.HIDE)
			hideAnimation();
	}

	private void showAnimation() {
		long time1 = Math.min(TIME_SLIDE, _totalTime);
		long time2 = Math.min(TIME_TEXT, Math.max(_totalTime - TIME_SLIDE, 0));
		
		Log.d("time2", "" + time2);
		
		float t1 = (float)time1 / TIME_SLIDE;
		float t2 = (float)time2 / TIME_TEXT;
		
		_curWidth = t1 * MAX_WIDTH;
		
		//_outerCircle.setX(_curWidth);
		//_innerCircle.setX(_curWidth);
		
		//removeObject(_outerRect);
		//removeObject(_innerRect);
		
		//_outerRect = new ModelRect(_curWidth, 200, 0, -100, 0xff006699);
		//_innerRect = new ModelRect(_curWidth, 186, 0, -93, 0xff00ccff);
		
		//_outerRect.setDepth(92);
		//_innerRect.setDepth(93);
		
		//addObject(_innerRect);
		//addObject(_outerRect);
		
		_gameOverText.getPaint().setAlpha((int) (255 * t2));
		_gameOverTextStroke.getPaint().setAlpha((int) (255 * t2));
		//_lvlText.getPaint().setAlpha((int) (255 * t2));
		_scoreTextStroke.getPaint().setAlpha((int) (255 * t2));
		_scoreText.getPaint().setAlpha((int) (255 * t2));
		_bestScoreText.getPaint().setAlpha((int) (255 * t2));
		_bestScoreTextStroke.getPaint().setAlpha((int) (255 * t2));
		
		if (t2 == 1)
			_state = State.VISIBLE;
	}
	
	private void hideAnimation() {
		long time1 = Math.min(TIME_TEXT, _totalTime);
		long time2 = Math.min(TIME_SLIDE, Math.max(_totalTime - TIME_TEXT, 0));
		
		float t1 = 1 - (float)time1 / TIME_TEXT;
		float t2 = 1 - (float)time2 / TIME_SLIDE;
		
		_gameOverText.getPaint().setAlpha((int) (255 * t1));
		_gameOverTextStroke.getPaint().setAlpha((int) (255 * t1));
		//_lvlText.getPaint().setAlpha((int) (255 * t1));
		_scoreTextStroke.getPaint().setAlpha((int) (255 * t1));
		_scoreText.getPaint().setAlpha((int) (255 * t1));
		_bestScoreText.getPaint().setAlpha((int) (255 * t1));
		_bestScoreTextStroke.getPaint().setAlpha((int) (255 * t1));
		_curWidth = t2 * MAX_WIDTH;
		
		//_outerCircle.setX(_curWidth);
		//_innerCircle.setX(_curWidth);
		
		//removeObject(_outerRect);
		//removeObject(_innerRect);
		
		//_outerRect = new ModelRect(_curWidth, 200, 0, -100, 0xff006699);
		//_innerRect = new ModelRect(_curWidth, 186, 0, -93, 0xff00ccff);
		
		//_outerRect.setDepth(92);
		//_innerRect.setDepth(93);
		
		//addObject(_innerRect);
		//addObject(_outerRect);
		
		if (t2 == 0) {
			_state = State.HIDDEN;
			//removeObject(_innerCircle);
			//removeObject(_innerRect);
			//removeObject(_outerCircle);
			//removeObject(_outerRect);
			removeObject(_gameOverText);
			removeObject(_gameOverTextStroke);
			//removeObject(_lvlText);
			removeObject(_scoreText);
			removeObject(_scoreTextStroke);
			removeObject(_bestScoreText);
			removeObject(_bestScoreTextStroke);
			
			//_innerCircle = null;
			//_innerRect = null;
			//_outerCircle = null;
			//_outerRect = null;
			_gameOverText = null;
			_gameOverTextStroke = null;
			_lvlText = null;
			_scoreText = null;
			_scoreTextStroke = null;
			_bestScoreText = null;
			_bestScoreTextStroke = null;
		}
	}
	
}
