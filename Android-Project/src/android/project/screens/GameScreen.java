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

	public GameScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		_currentScreen = CurrentScreen.LOAD;
		_menu = new ModelMenuScreen();
		_background = new ModelGameBackground();
		_play = new ModelPlayScreen();

		getWorld().addObject(new ModelLogoScreen());
		getWorld().addObject(_menu);
		getWorld().addObject(_play);

		_totalTime = 0;
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
		_menu.hide();
		_play.show();
		_currentScreen = CurrentScreen.PLAY;
	}
	public void endGame(){
		_play.hide();
		_menu.show();
		_currentScreen = CurrentScreen.MENU;
	}

}
