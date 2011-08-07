package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;
import android.util.Log;

public class ModelGameMenu extends Object2D {
	private enum Menu {
		PLAY, SETTINGS, NONE
	}
	
	private Menu _menu;
	private Menu _loadMenu;
	private Menu _loadingMenu;
	private Object2D[] _finalizeButtons;
	private Object2D[] _buttons;
	private long _time;
	int _pressingButton;
	
	public ModelGameMenu(float x, float y) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		
		_menu = Menu.NONE;
		_loadMenu = Menu.PLAY;
		_buttons = null;
		_finalizeButtons = null;
		_pressingButton = -1;
	}

	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		if (_loadMenu != Menu.NONE) {
			_time = 0;
			_loadingMenu = _loadMenu;
			_loadMenu = Menu.NONE;
			
			closeMenu();
			if (_loadingMenu == Menu.SETTINGS)
				_buttons = getSettingsMenu();
			else if (_loadingMenu == Menu.PLAY)
				_buttons = getPlayMenu();
			openMenu();
			return;
		}
		if (_loadingMenu != Menu.NONE) {
			_time += timeDiff;
			if (_time > 300) {
				finalizeMenu();
				_menu = _loadingMenu;
				_loadingMenu = Menu.NONE;
				_loadMenu = Menu.NONE;
			}
		}
		
		/*
		_movingSettings.freeInnerObject();
		_movingSocial.freeInnerObject();
		_movingQuit.freeInnerObject();
		
		addObject(_floatingSettings);
		addObject(_floatingSocial);
		addObject(_floatingQuit);
		
		_floatingSettings.freeInnerObject();
		_floatingSocial.freeInnerObject();
		_floatingQuit.freeInnerObject();
		
		Object2D scalingSettings = new ModelScaleObject(_settingsButton, 1, 0.01f, 1000);
		Object2D scalingSocial = new ModelScaleObject(_socialButton, 1, 0.01f, 2000);
		Object2D scalingQuit = new ModelScaleObject(_quitButton, 1, 0.01f, 3000);
		
		addObject(new ModelThrownObject(scalingSettings, -100, 120, 10));
		addObject(new ModelThrownObject(scalingSocial, -100, 110, 10));
		addObject(new ModelThrownObject(scalingQuit, -100, 100, 10));
		
		_socialButton.setDepth(-100);
		
		//addObject(new ModelScaleObject(new ModelSettingsButtonBig(0, 0), 0.01f, 1f, 300));
		//addObject(new ModelScaleObject(_scalingPlay.freeInnerObject(), 1, 0.01f, 300));
		
		
		_settingsButton = new ModelSettingsButton();
		_socialButton = new ModelSocialButton();
		_quitButton = new ModelQuitButton();
		
		_floatingSettings = new ModelFloatingObject(_settingsButton, 0, 0, 3, 1000, 10000);
		_floatingSocial = new ModelFloatingObject(_socialButton, 0, 0, 3, 1000, 8000);
		_floatingQuit = new ModelFloatingObject(_quitButton, 0, 0, 3, 1000, 12000);
		
		_movingSettings = new ModelMoveObject(_floatingSettings, -130, -90, 300);
		_movingSocial = new ModelMoveObject(_floatingSocial, -160, 0, 300);
		_movingQuit = new ModelMoveObject(_floatingQuit, -130, 90, 300);
		
		addObject(_movingSettings);
		addObject(_movingSocial);
		addObject(_movingQuit);
		*/
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
	
	private void closeMenu() {
		if (_buttons == null)
			return;
		_finalizeButtons = _buttons;
		
		for (int i = 1; i < _buttons.length; ++i) {
			_buttons[i] = _buttons[i].getParent().getParent();
		}
		
		_buttons[0] = ((ModelScaleObject)_buttons[0].getParent()).freeInnerObject();
		addObject(new ModelScaleObject(_buttons[0], 1, 0.01f, 300));
		
		for (int i = 1; i < _buttons.length; ++i) { 
			addObject(_buttons[i] = ((ModelMoveObject)_buttons[i]).freeInnerObject());
			_buttons[i] = ((ModelFloatingObject)_buttons[i]).freeInnerObject();
		}
		addObject(new ModelThrownObject(_buttons[1], -100, 120, 10));
		addObject(new ModelThrownObject(_buttons[2], -100, 110, 10));
		addObject(new ModelThrownObject(_buttons[3], -100, 100, 10));
		
		_finalizeButtons = _buttons;
	}
	
	private void openMenu() {
		Object2D[] wrappers = new Object2D[_buttons.length];
		
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
	
	private Menu getAction(int index) {
		if (_menu == Menu.PLAY)
			if (index == 0)
				return Menu.NONE;
			else if (index == 1)
				return Menu.SETTINGS;
			else if (index == 2)
				return Menu.NONE;
			else
				return Menu.NONE;
		else if (_menu == Menu.SETTINGS)
			if (index == 0)
				return Menu.PLAY;
			else if (index == 1)
				return Menu.NONE;
			else if (index == 2)
				return Menu.NONE;
			else
				return Menu.NONE;
		return Menu.NONE;
	}
	
	public void press(float x, float y) {
		if (_loadMenu != Menu.NONE || _loadingMenu != Menu.NONE)
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
		if (_loadMenu != Menu.NONE || _loadingMenu != Menu.NONE)
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
		if (_loadMenu != Menu.NONE || _loadingMenu != Menu.NONE)
			return;
		if (_pressingButton == -1)
			return;
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton) {
					_loadMenu =  getAction(i);
					Log.d("", "" + _menu.name());
					Log.d("", "" + _loadMenu.name());
				}
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;
	}
	
}
