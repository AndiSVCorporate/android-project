package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;

public class ModelGameMenu extends Object2D {

	ModelPlayButton _playButton;
	ModelSettingsButton _settingsButton;
	ModelSocialButton _socialButton;
	ModelQuitButton _quitButton;
	
	ModelFloatingObject _containerSettings;
	ModelFloatingObject _containerSocial;
	ModelFloatingObject _containerQuit;
	
	public ModelGameMenu(float x, float y) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		
		_playButton = new ModelPlayButton(0, 0);
		_settingsButton = new ModelSettingsButton();
		_socialButton = new ModelSocialButton();
		_quitButton = new ModelQuitButton();
		
		_containerSettings = new ModelFloatingObject(_settingsButton, -130, -90, 3, 1000, 10000);
		_containerSocial = new ModelFloatingObject(_socialButton, -160, 0, 3, 1000, 8000);
		_containerQuit = new ModelFloatingObject(_quitButton, -130, 90, 3, 1000, 12000);
		
		addObject(_playButton);
		addObject(_containerSettings);
		addObject(_containerSocial);
		addObject(_containerQuit);
	}

	@Override
	public void drawThis(Canvas c) { }
	
	
	private long _totalTime = 0;
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime < 1000)
			return;
		Object2D o1 = _containerSettings.freeInnerObject();
		if (o1 != null)
			addObject(new ModelThrownObject(o1, -100, 100, 10));
		Object2D o2 = _containerSocial.freeInnerObject();
		if (o2 != null)
			addObject(new ModelThrownObject(o2, -100, 100, 10));
		Object2D o3 = _containerQuit.freeInnerObject();
		if (o3 != null)
			addObject(new ModelThrownObject(o3, -100, 100, 10));
	}
}
