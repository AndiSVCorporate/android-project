package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.Utils;
import android.project.models.ModelGameBackground;
import android.project.models.ModelPlayScreen;
import android.project.models.ModelLogoScreen;
import android.project.models.ModelMenuScreen;
import android.util.Log;
import android.view.MotionEvent;

public class GameScreen extends Screen {

	private enum CurrentScreen {
		LOAD, MENU, PLAY
	}

	private ModelMenuScreen _menu;
	private ModelPlayScreen _play;
	private ModelGameBackground _background;

	private CurrentScreen _currentScreen;

	private long _totalTime;
	
	private boolean _playing;
	private boolean _gameAvailable;

	public GameScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);

		Utils.initializeOpenfeint();
		Utils.setVibration(true);

		_currentScreen = CurrentScreen.LOAD;
		_menu = new ModelMenuScreen();
		_background = new ModelGameBackground();
		//_play = new ModelPlayScreen();
		getWorld().addObject(new ModelLogoScreen());
		getWorld().addObject(_menu);
		//getWorld().addObject(_play);
				
		_totalTime = 0;
		_playing = false;
		_gameAvailable = false;
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (_currentScreen == CurrentScreen.MENU)
			return _menu.onTouchEvent(event);
		if (_currentScreen == CurrentScreen.PLAY) {
			_menu.onTouchEvent(event);
			return _play.onTouchEvent(event);
		}
		if (_currentScreen == CurrentScreen.LOAD)
			return _menu.onTouchEvent(event);
		return true;
	}

	@Override
	public void onBackPressed() {
		if (_currentScreen == CurrentScreen.MENU)
			_menu.onBackPressed();
		else if (_currentScreen == CurrentScreen.PLAY)
			_play.onBackPressed();
		else if (_currentScreen == CurrentScreen.LOAD)
			_menu.onBackPressed();
	}

	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime > 2300) {
			if (Utils.getBackground() && _background.getParent() == null)
				getWorld().addObject((_background = new ModelGameBackground()));
			if (!Utils.getBackground() && _background.getParent() != null)
				getWorld().removeObject(_background);
			
		}
		stopCalculate(timeDiff);
	}
	
	private boolean _stopCalculate = false;
	private long _stopCalculateTime = 0;
	private void stopCalculate(long timeDiff) {
		if (_stopCalculate) {
			_stopCalculateTime += timeDiff;
			_stopCalculate = false;
		}
		if (_stopCalculateTime == 0)
			return;
		_stopCalculateTime += timeDiff;
		if (_stopCalculateTime > 350) {
			if (getWorld().removeObject(_background))
				getWorld().addObject((_background = new ModelGameBackground()));
			
			getWorld().removeObject(_play);
			_play = null;
			_stopCalculateTime = 0;
		}
	}

	@Override
	public int getBorderColor() {
		return Color.BLACK;
	}

	public void showMenu() {
		_menu.show();
	}

	public void hideMenu() {
		_menu.hide();
	}

	public void startGame() {
		_playing = true;
		_gameAvailable = true;
		_menu.hide();
		_play = new ModelPlayScreen();
		getWorld().addObject(_play);
		_play.show();

		_currentScreen = CurrentScreen.PLAY;
	}
	
	public void continueGame() {
		_playing = true;
		_menu.hideReturn();

		_currentScreen = CurrentScreen.PLAY;
	}
	
	public void pauseGame() {
		_playing = false;
		_menu.pause();
		_play.pause();

		_currentScreen = CurrentScreen.MENU;
	}
	
	public void GameOver() {
		saveHighscore();
		_playing = false;
		_gameAvailable = false;
		_menu.gameOver();
		_play.setDepthRecursive(-30000);
		_background.setDepthRecursive(-30000);
		_play.stopGame();
		_stopCalculate = true;
		_currentScreen = CurrentScreen.MENU;
	}
	
	public void stopGame(int score) {
		saveHighscore();
		_menu.stopGame();
		_play.setDepthRecursive(-30000);
		_background.setDepthRecursive(-30000);
		_play.stopGame();
		_stopCalculate = true;
		_gameAvailable = false;
		_currentScreen = CurrentScreen.MENU;
	}

	public boolean isPlaying() {
		return _playing;
	}
	public ModelPlayScreen getPlay(){
		return _play;
	}
	public boolean isGameAvailable() {
		return _gameAvailable;
	}
	public int saveHighscore(){
		return Utils.saveHighscore(_play.getScore());
	}
}
