package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;

public class ModelGameMenu extends Object2D {

	private ModelPlayButton _playButton;
	private ModelSettingsButton _settingsButton;
	private ModelSocialButton _socialButton;
	private ModelQuitButton _quitButton;
	
	private ModelFloatingObject _floatingSettings;
	private ModelFloatingObject _floatingSocial;
	private ModelFloatingObject _floatingQuit;
	
	private ModelMoveObject _movingSettings;
	private ModelMoveObject _movingSocial;
	private ModelMoveObject _movingQuit;
	
	private ModelScaleObject _scalingPlay;
	
	public ModelGameMenu(float x, float y) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		
		_playButton = new ModelPlayButton(0, 0);
		_settingsButton = new ModelSettingsButton();
		_socialButton = new ModelSocialButton();
		_quitButton = new ModelQuitButton();
		
		_floatingSettings = new ModelFloatingObject(_settingsButton, 0, 0, 3, 1000, 10000);
		_floatingSocial = new ModelFloatingObject(_socialButton, 0, 0, 3, 1000, 8000);
		_floatingQuit = new ModelFloatingObject(_quitButton, 0, 0, 3, 1000, 12000);
		
		_movingSettings = new ModelMoveObject(_floatingSettings, -130, -90, 300);
		_movingSocial = new ModelMoveObject(_floatingSocial, -160, 0, 300);
		_movingQuit = new ModelMoveObject(_floatingQuit, -130, 90, 300);
		
		_scalingPlay = new ModelScaleObject(_playButton, 0.01f, 1f, 300);
		addObject(_scalingPlay);
		addObject(_movingSettings);
		addObject(_movingSocial);
		addObject(_movingQuit);
	}

	@Override
	public void drawThis(Canvas c) { }
	
	
	private boolean done = false;
	
	private long _totalTime = 0;
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime < 1000)
			return;
		if (done)
			return;
		done = true;
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
		
		addObject(new ModelScaleObject(new ModelSettingsButtonBig(0, 0), 0.01f, 1f, 300));
		addObject(new ModelScaleObject(_scalingPlay.freeInnerObject(), 1, 0.01f, 300));
		
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
	}
}
