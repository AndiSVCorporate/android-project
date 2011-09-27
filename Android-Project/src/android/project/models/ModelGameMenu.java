package android.project.models;

import java.util.Random;

import com.openfeint.api.OpenFeint;
import com.openfeint.api.ui.Dashboard;
import android.project.SoundManager;
import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Position;
import android.project.Utils;
import android.project.bounds.BoundsCircle;
import android.project.screens.GameScreen;
import android.util.Log;

public class ModelGameMenu extends Object2D {

	private enum Menu {
		PLAY, SETTINGS, SOCIAL, PAUSE, RESUME, GAME_OVER
	}

	private enum Action {
		LOAD_MENU_PLAY, LOAD_MENU_SETTINGS, LOAD_MENU_SOCIAL, LOAD_MENU_PAUSE, LOAD_MENU_RESUME, LOAD_MENU_GAME_OVER,
		LOAD_BUTTON_QUIT, LOAD_BUTTON_SOUND, LOAD_BUTTON_BACKGROUND, LOAD_BUTTON_VIBRATOR,
		RELOAD_PLAY,
		IDLE
	}

	private Menu _menu;
	private ModelGameMenuGameOver _gameOver;
	private Action _nextAction;
	private Action _currentAction;
	private Action _lastAction;
	private Object2D[] _finalizeButtons;
	private Object2D _finalizeButton;
	private Object2D[] _buttons;
	private long _time;
	int _pressingButton;
	int _fadeOut;
	private ModelHighscore _highscore;
	public ModelGameMenu(float x, float y) {
		super(null, null, new Position(x, y), false, false, false, null);

		_menu = Menu.PLAY;
		_nextAction = Action.LOAD_MENU_PLAY;
		_currentAction = Action.IDLE;
		_lastAction = Action.IDLE;

		_highscore=new ModelHighscore();
		addObject(_highscore);
		_highscore.setX(150);
		_highscore.setY(-300);
		_buttons = null;
		_finalizeButtons = null;
		_pressingButton = -1;

		_gameOver = new ModelGameMenuGameOver();
		_fadeOut = 0;
		
		addObject(_gameOver);
		
		//anchor object, do not remove
		addObject(new Object2D());
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		_fadeOut -= timeDiff;
		if (_fadeOut > 0) {
			return;
		}
		
		if (_nextAction != Action.IDLE) {
			_time = 0;
			_currentAction = _nextAction;
			_nextAction = Action.IDLE;

			if (_currentAction == Action.LOAD_MENU_SETTINGS) {
				changeMenu(getSettingsMenu(), Menu.SETTINGS);
			} else if (_currentAction == Action.LOAD_MENU_PLAY) {
				if (((GameScreen)getScreen()).isGameAvailable())
					changeMenu(getResumeMenu(), Menu.RESUME);
				else
					changeMenu(getPlayMenu(), Menu.PLAY);
			} else if (_currentAction == Action.LOAD_MENU_SOCIAL) {
				changeMenu(getSocialMenu(), Menu.SOCIAL);
			} else if (_currentAction == Action.LOAD_MENU_PAUSE) {
				if (_menu == Menu.GAME_OVER) {
					((GameScreen)getScreen()).restartGame();
				}
				changeMenu(getPauseMenu(), Menu.PAUSE);
			} else if (_currentAction == Action.LOAD_MENU_RESUME) {
				changeMenu(getResumeMenu(), Menu.RESUME);
				((GameScreen)getScreen()).pauseGame();
			} else if (_currentAction == Action.LOAD_MENU_GAME_OVER) {
				changeMenu(getGameOverMenu(), Menu.GAME_OVER);
			} else if (_currentAction == Action.LOAD_BUTTON_QUIT) {
				changeButton(new ModelQuitConfirmButton(), 3);
			} else if (_currentAction == Action.LOAD_BUTTON_SOUND) {
				changeButton((Utils.getSound() ? new ModelSoundOnButton() : new ModelSoundOffButton()), 1);
			} else if (_currentAction == Action.LOAD_BUTTON_BACKGROUND) {
				changeButton((Utils.getBackground() ? new ModelBackgroundOnButton() : new ModelBackgroundOffButton()), 2);
			} else if(_currentAction == Action.LOAD_BUTTON_VIBRATOR) {
				changeButton((Utils.getVibration() ? new ModelVibeOnButton() : new ModelVibeOffButton()), 3);
				if(Utils.getVibration())
					Utils.vibrate(500);
			} else if (_currentAction == Action.RELOAD_PLAY) {
				changeButton(new ModelQuitButton(), 3);
				changeButton(new ModelPlayButton(0, 0), 0);
				_menu = Menu.PLAY;
			}
			return;
		}
		if (_currentAction != Action.IDLE) {
			_time += timeDiff;
			if (_time > 300) {
					finalizeMenu();
					finalizeButton();
				_lastAction = _currentAction;
				if (_currentAction != Action.LOAD_MENU_GAME_OVER)
					_currentAction = Action.IDLE;
				else {
					_gameOver.show(Utils.get_lastScore(), 2, 1);
					if (_time > 900)
						_currentAction = Action.IDLE;	
				}
			}
		}
	}

	private Object2D[] getPlayMenu() {
		return new Object2D[] {
				new ModelPlayButton(0, 0),
				new ModelSettingsButton(),
				new ModelSocialButton(),
				new ModelQuitButton()
		};
	}

	private Object2D[] getSettingsMenu() {
		return new Object2D[] {
				new ModelSettingsButtonBig(0, 0),
				(Utils.getSound() ? new ModelSoundOnButton() : new ModelSoundOffButton()),
				(Utils.getBackground() ? new ModelBackgroundOnButton() : new ModelBackgroundOffButton()),
				(Utils.getVibration() ? new ModelVibeOnButton() : new ModelVibeOffButton()),
		};
	}

	private Object2D[] getSocialMenu() {
		return new Object2D[] {
				new ModelSocialButtonBig(0, 0),
				new ModelHighscoresButton(),
				new ModelFacebookButton(),
				new ModelOpenFeintButton()
		};
	}

	private Object2D[] getPauseMenu() {
		return new Object2D[] {
				new ModelPauseButtonBig(),
				new Object2D(),
				new Object2D(),
				new Object2D()
		};
	}
	
	private Object2D[] getResumeMenu() {
		return new Object2D[] {
				new ModelResumeButton(),
				new ModelSettingsButton(),
				new ModelSocialButton(),
				new ModelStopButton()
		};
	}
	
	private Object2D[] getGameOverMenu() {
		return new Object2D[] {
				new ModelReplayButton(),
				new ModelFacebookSubmitButton(),
				new ModelOpenFeintSubmitButton(),
				new ModelStopButton()
		};
	}

	private void changeMenu(Object2D[] newMenu, Menu menu) {
		closeMenu();
		_buttons = newMenu;
		openMenu();
		_menu = menu;
	}

	private void changeButton(Object2D newButton, int i) {
		freeInnerObject(getWorld(), _buttons[i]);
		_buttons[i].setDepthRecursive(-200);
		if (i == 0) {
			getWorld().addObject(new ModelScaleObject(_buttons[0], 1, 0.01f, 300));
			_finalizeButton = _buttons[0];
			_buttons[0] = new ModelScaleObject(newButton, 0.01f, 1f, 300);
		} else if (i == 1) {
			getWorld().addObject(new ModelThrownObject(new ModelScaleObject(_buttons[1], 1, 0.5f, 300), -100, 120, 10));
			_buttons[1] = new ModelFloatingObject(newButton, 3, 1000, 10000);
			_buttons[1] = new ModelMoveObject(_buttons[1], -130, -90, 300);
		} else if (i == 2) {
			getWorld().addObject(new ModelThrownObject(new ModelScaleObject(_buttons[2], 1, 0.5f, 300), -100, 110, 10));
			_buttons[2] = new ModelFloatingObject(newButton, 3, 1000, 8000);
			_buttons[2] = new ModelMoveObject(_buttons[2], -160, 0, 300);
		} else if (i == 3) {
			getWorld().addObject(new ModelThrownObject(new ModelScaleObject(_buttons[3], 1, 0.5f, 300), -100, 100, 10));
			_buttons[3] = new ModelFloatingObject(newButton, 3, 1000, 12000);
			_buttons[3] = new ModelMoveObject(_buttons[3], -130, 90, 300);
		}
		addObject(_buttons[i]);
		_buttons[i] = newButton;
	}

	private void closeMenu() {
		if (_buttons == null)
			return;
		freeInnerObject(getWorld(), _buttons[0]);
		getWorld().addObject(new ModelScaleObject(_buttons[0], 1, 0.01f, 300));
		_buttons[0].setDepthRecursive(-200);
		for (int i = 1; i < _buttons.length; ++i) {
			freeInnerObject(getWorld(), _buttons[i]);
			_buttons[i].setDepthRecursive(-200);
		}
		getWorld().addObject(new ModelThrownObject(new ModelScaleObject(_buttons[1], 1, 0.5f, 300), -100, 120, 10));
		getWorld().addObject(new ModelThrownObject(new ModelScaleObject(_buttons[2], 1, 0.5f, 300), -100, 110, 10));
		getWorld().addObject(new ModelThrownObject(new ModelScaleObject(_buttons[3], 1, 0.5f, 300), -100, 100, 10));

		_finalizeButtons = _buttons;
	}

	private void openMenu() {
		Object2D[] wrappers = new Object2D[_buttons.length];

		wrappers[0] = new ModelScaleObject(_buttons[0], 0.01f, 1f, 300);

		wrappers[1] = new ModelFloatingObject(_buttons[1], 3, 1000, 10000);
		wrappers[2] = new ModelFloatingObject(_buttons[2], 3, 1000, 8000);
		wrappers[3] = new ModelFloatingObject(_buttons[3], 3, 1000, 12000);

		wrappers[1] = new ModelMoveObject(wrappers[1], -130, -90, 300);
		wrappers[2] = new ModelMoveObject(wrappers[2], -160, 0, 300);
		wrappers[3] = new ModelMoveObject(wrappers[3], -130, 90, 300);

		for (int i = 0; i < wrappers.length; ++i)
			addObject(wrappers[i]);
	}

	private void finalizeMenu() {
		if (_finalizeButtons == null)
			return;
		getWorld().removeObject(_finalizeButtons[0].getParent());
		_finalizeButtons = null;
	}

	private void finalizeButton() {
		if (_finalizeButton == null)
			return;
		getWorld().removeObject(_finalizeButton.getParent());
		_finalizeButton = null;
	}

	private Action getAction(int index) {
		Random generator = new Random();
		int randomNum = generator.nextInt(100);
		
		if(randomNum < 70)
			SoundManager.playFX(SoundManager.MENU_REGULAR_1);
		else
			SoundManager.playFX(SoundManager.MENU_REGULAR_2);
		
		if (_menu == Menu.PLAY) {
			if (index == 0) {
				((GameScreen)getScreen()).startGame();
				return Action.LOAD_MENU_PAUSE;
			} else if (index == 1)
				return Action.LOAD_MENU_SETTINGS;
			else if (index == 2)
				return Action.LOAD_MENU_SOCIAL;
			else
				if (_lastAction == Action.LOAD_BUTTON_QUIT)
					Utils.quit();
				else
					return Action.LOAD_BUTTON_QUIT;
		} else if (_menu == Menu.SETTINGS) {
			if (index == 0)
				return Action.LOAD_MENU_PLAY;
			else if (index == 1) {
				Utils.setSound(!Utils.getSound());
				return Action.LOAD_BUTTON_SOUND;
			}
			else if (index == 2) {
				Utils.setBackground(!Utils.getBackground());
				return Action.LOAD_BUTTON_BACKGROUND;
			}
			else if(index==3){
				Utils.setVibration(!Utils.getVibration());
				return Action.LOAD_BUTTON_VIBRATOR;
			}
		} else if (_menu == Menu.SOCIAL) {
			if (index == 0){
				_highscore.hide();
				return Action.LOAD_MENU_PLAY;
			}
			else if(index==1){
				if(_highscore.is_show())
					_highscore.hide();
				else
					_highscore.show();			
			}
			else if (index ==3){
				Utils.navigateToOpenfeint();
			}else if (index==2)
				Utils.navigateToFacebook();
		} else if (_menu == Menu.PAUSE) {
			Log.d("HI", "index"+index);
			if (index == 0)
				return Action.LOAD_MENU_RESUME;
		} else if (_menu == Menu.RESUME) {
			if (index == 0) {
				((GameScreen)getScreen()).continueGame();
				return Action.LOAD_MENU_PAUSE;
			} else if (index == 1)
				return Action.LOAD_MENU_SETTINGS;
			else if (index == 2)
				return Action.LOAD_MENU_SOCIAL;
			else {
				SoundManager.stopSong();
				((GameScreen)getScreen()).saveHighscore();
				return Action.LOAD_MENU_GAME_OVER;
			}
		} else if (_menu == Menu.GAME_OVER) {
			if (index == 0) {
				_gameOver.hide();
				_fadeOut = 300;
				return Action.LOAD_MENU_PAUSE;
			} else if (index == 3) {
				((GameScreen)getScreen()).stopGame(1);
				_gameOver.hide();
				_fadeOut = 300;
				return Action.LOAD_MENU_PLAY;
			}
			if(index==1){
				Utils.postHighscore(Utils.get_lastScore());
			}
			if(index==2){
				Utils.postToOpenFeint(Utils.get_lastScore());
			}
		}
		return Action.IDLE;
	}

	public void gameOver() {
		Log.d("over","gameOver2");
		SoundManager.stopSong();
		if (_menu == Menu.PAUSE)
			_nextAction = Action.LOAD_MENU_GAME_OVER;
	}

	public void onBackPressed() {
		if (_currentAction != Action.IDLE || _nextAction != Action.IDLE)
			return;
		if (_pressingButton != -1) {
			_buttons[_pressingButton].scale((1/1.2f));
			_pressingButton = -1;
		}
		Log.d("BACKPRESS", "" + _menu.toString());
		Log.d("BACKPRESS", "" + _menu.toString());
		Log.d("BACKPRESS", "" + _menu.toString());
		Log.d("BACKPRESS", "" + _menu.toString());
		Log.d("BACKPRESS", "" + _menu.toString());
		
		if (_menu == Menu.PLAY) {
			if (_lastAction == Action.LOAD_BUTTON_QUIT)
				Utils.quit();
			else
				_nextAction = Action.LOAD_BUTTON_QUIT;
		} else if (_menu == Menu.SETTINGS) {
			_nextAction = Action.LOAD_MENU_PLAY;
		} else if (_menu == Menu.SOCIAL) {
			_highscore.hide();
			_nextAction = Action.LOAD_MENU_PLAY;
		} else if (_menu == Menu.PAUSE) {
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			Log.d("what the fuck", "seriously");
			
			_nextAction = Action.LOAD_MENU_RESUME;
		} else if (_menu == Menu.RESUME) {
			((GameScreen)getScreen()).continueGame();
			_nextAction = Action.LOAD_MENU_PAUSE;
		} else if (_menu == Menu.GAME_OVER) {
			((GameScreen)getScreen()).stopGame(1);
			_gameOver.hide();
			_fadeOut = 300;
			_nextAction = Action.LOAD_MENU_PLAY;
		}
	}

	public void press(float x, float y) {
		if (_currentAction != Action.IDLE || _nextAction != Action.IDLE)
			return;
		_highscore.press(x, y);
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y)) {
				_pressingButton = i;
				_buttons[i].scale(1.2f);
				return;
			}
		_pressingButton = -1;
	}

	public void move(float x, float y) {
		if (_currentAction != Action.IDLE || _nextAction != Action.IDLE)
			return;
		if (_pressingButton == -1)
			return;
		_highscore.move(x, y);
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton)
					return;
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;

	}

	public void release(float x, float y) {
		_highscore.release(x, y);
		if (_currentAction != Action.IDLE || _nextAction != Action.IDLE)
			return;
		if (_pressingButton == -1)
			return;
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton) {
					_nextAction =  getAction(i);
				}
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;
	}

}
