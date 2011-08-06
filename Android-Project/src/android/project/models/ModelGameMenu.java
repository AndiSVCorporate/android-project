package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Positioning;

public class ModelGameMenu extends Object2D {

	ModelPlayButton _playButton;
	ModelSettingsButton _settingsButton;
	ModelSocialButton _socialButton;
	ModelQuitButton _quitButton;
	
	public ModelGameMenu(float x, float y) {
		super(null, null, new Positioning(x, y, 1, 1, 0), false, false, false, null);
		
		_playButton = new ModelPlayButton(0, 0);
		_settingsButton = new ModelSettingsButton();
		_socialButton = new ModelSocialButton();
		_quitButton = new ModelQuitButton();
		
		addObject(_playButton);
		addObject(new ModelFloatingModel(_settingsButton, -130, -90, 3, 1000, 10000));
		addObject(new ModelFloatingModel(_socialButton, -160, 0, 3, 1000, 8000));
		addObject(new ModelFloatingModel(_quitButton, -130, 90, 3, 1000, 12000));
		
		addObject(new ModelBezierCurve(_playButton, _settingsButton, 0xff92cc47, 0xffffcc00, 104, 40));
		addObject(new ModelBezierCurve(_playButton, _socialButton, 0xff92cc47, 0xff0099ff, 104, 40));
		addObject(new ModelBezierCurve(_playButton, _quitButton, 0xff92cc47, 0xff8d2036, 104, 40));
	}

	@Override
	public void drawThis(Canvas c) { }
}
