package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;
import android.project.Utils;
import android.util.Log;

public class ModelGameMenu extends Object2D {
	private enum Menu {
		PLAY, SETTINGS
	}
	
	private enum Action {
		LOAD_MENU_PLAY, LOAD_MENU_SETTINGS,
		LOAD_BUTTON_QUIT,
		IDLE
	}
	
	private Menu _menu;
	private Action _nextAction;
	private Action _currentAction;
	private Action _lastAction;
	private Object2D[] _finalizeButtons;
	private Object2D[] _buttons;
	private long _time;
	int _pressingButton;
	
	public ModelGameMenu(float x, float y) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		
		_menu = Menu.PLAY;
		_nextAction = Action.LOAD_MENU_PLAY;
		_currentAction = Action.IDLE;
		_lastAction = Action.IDLE;
		
		_buttons = null;
		_finalizeButtons = null;
		_pressingButton = -1;
	}

	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		if (_nextAction != Action.IDLE) {
			_time = 0;
			_currentAction = _nextAction;
			_nextAction = Action.IDLE;
			
			if (_currentAction == Action.LOAD_MENU_SETTINGS) {
				changeMenu(getSettingsMenu(), Menu.SETTINGS);
			} else if (_currentAction == Action.LOAD_MENU_PLAY) {
				changeMenu(getPlayMenu(), Menu.PLAY);
			} else if (_currentAction == Action.LOAD_BUTTON_QUIT) {
				changeButton(new ModelQuitConfirmButton(), 3);
			}
			return;
		}
		if (_currentAction != Action.IDLE) {
			_time += timeDiff;
			if (_time > 300) {
				if (_currentAction == Action.LOAD_MENU_SETTINGS ||
						_currentAction == Action.LOAD_MENU_PLAY)
					finalizeMenu();
				_lastAction = _currentAction;
				_currentAction = Action.IDLE;
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
				new ModelSettingsButton(),
				new ModelSocialButton(),
				new ModelQuitButton()
				};
	}
	
	private void changeMenu(Object2D[] newMenu, Menu menu) {
		closeMenu();
		_buttons = newMenu;
		openMenu();
		_menu = menu;
	}
	
	private void changeButton(Object2D newButton, int i) {
		_buttons[i] = _buttons[i].getParent().getParent();
		addObject(_buttons[i] = ((ModelMoveObject)_buttons[i]).freeInnerObject());
		_buttons[i] = ((ModelFloatingObject)_buttons[i]).freeInnerObject();
		_buttons[i].setDepthRecursive(-1000);
		if (i == 1) {
			addObject(new ModelThrownObject(_buttons[1], -100, 120, 10));
			_buttons[1] = new ModelFloatingObject(newButton, 0, 0, 3, 1000, 10000);
			_buttons[1] = new ModelMoveObject(_buttons[1], -130, -90, 300);
		} else if (i == 2) {
			addObject(new ModelThrownObject(_buttons[2], -100, 110, 10));
			_buttons[2] = new ModelFloatingObject(newButton, 0, 0, 3, 1000, 8000);
			_buttons[2] = new ModelMoveObject(_buttons[2], -160, 0, 300);
		} else if (i == 3) {
			addObject(new ModelThrownObject(_buttons[i], -100, 100, 10));
			_buttons[3] = new ModelFloatingObject(newButton, 0, 0, 3, 1000, 12000);
			_buttons[3] = new ModelMoveObject(_buttons[3], -130, 90, 300);
		}
		addObject(_buttons[i]);
		_buttons[i] = newButton;
	}
	
	private void closeMenu() {
		if (_buttons == null)
			return;
		_finalizeButtons = _buttons;
		
		for (int i = 1; i < _buttons.length; ++i) {
			_buttons[i] = _buttons[i].getParent().getParent();
		}
		
		_buttons[0] = ((ModelScaleObject)_buttons[0].getParent()).freeInnerObject();
		addObject(new ModelScaleObject(_buttons[0], 1, 0.01f, 300));
		_buttons[0].setDepthRecursive(-1000);
		for (int i = 1; i < _buttons.length; ++i) { 
			addObject(_buttons[i] = ((ModelMoveObject)_buttons[i]).freeInnerObject());
			_buttons[i] = ((ModelFloatingObject)_buttons[i]).freeInnerObject();
			_buttons[i].setDepthRecursive(-1000);
		}
		addObject(new ModelThrownObject(new ModelScaleObject(_buttons[1], 1, 0.5f, 300), -100, 120, 10));
		addObject(new ModelThrownObject(new ModelScaleObject(_buttons[2], 1, 0.5f, 300), -100, 110, 10));
		addObject(new ModelThrownObject(new ModelScaleObject(_buttons[3], 1, 0.5f, 300), -100, 100, 10));
		
		_finalizeButtons = _buttons;
	}
	
	private void openMenu() {
		Object2D[] wrappers = new Object2D[_buttons.length];
		
		Log.d("?", _buttons[0].getClass().getName() + " " + _buttons[0].depth());
		
		wrappers[0] = new ModelScaleObject(_buttons[0], 0.01f, 1f, 300);
		
		wrappers[1] = new ModelFloatingObject(_buttons[1], 0, 0, 3, 1000, 10000);
		wrappers[2] = new ModelFloatingObject(_buttons[2], 0, 0, 3, 1000, 8000);
		wrappers[3] = new ModelFloatingObject(_buttons[3], 0, 0, 3, 1000, 12000);
		
		wrappers[1] = new ModelMoveObject(wrappers[1], -130, -90, 300);
		wrappers[2] = new ModelMoveObject(wrappers[2], -160, 0, 300);
		wrappers[3] = new ModelMoveObject(wrappers[3], -130, 90, 300);
		
		for (int i = 0; i < wrappers.length; ++i)
			addObject(wrappers[i]);
	}
	
	private void finalizeMenu() {
		if (_finalizeButtons == null)
			return;
		removeObject(_finalizeButtons[0].getParent());
	}
	
	private Action getAction(int index) {
		if (_menu == Menu.PLAY)
			if (index == 0)
				return Action.IDLE;
			else if (index == 1)
				return Action.LOAD_MENU_SETTINGS;
			else if (index == 2)
				return Action.IDLE;
			else
				if (_lastAction == Action.LOAD_BUTTON_QUIT)
					Utils.quit();
				else
					return Action.LOAD_BUTTON_QUIT;
		else if (_menu == Menu.SETTINGS)
			if (index == 0)
				return Action.LOAD_MENU_PLAY;
			else if (index == 1)
				return Action.IDLE;
			else if (index == 2)
				return Action.IDLE;
			else
				return Action.IDLE;
		return Action.IDLE;
	}
	
	public void press(float x, float y) {
		if (_currentAction != Action.IDLE || _nextAction != Action.IDLE)
			return;
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
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton)
					return;
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;
	}
	
	public void release(float x, float y) {
		if (_currentAction != Action.IDLE || _nextAction != Action.IDLE)
			return;
		if (_pressingButton == -1)
			return;
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton) {
					_nextAction =  getAction(i);
					Log.d("", "" + _menu.name());
					Log.d("", "" + _nextAction.name());
				}
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;
	}
	
}
