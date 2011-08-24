package android.project.models;

import android.project.Object2D;
import android.project.screens.GameScreen;

public class ModelLogoScreen extends Object2D {
	
	private ModelCompanyLogo _companyLogo;
	private long _totalTime;
	private boolean _hide;
	
	public ModelLogoScreen() {
		_companyLogo = new ModelCompanyLogo();
		_totalTime = 0;
		_hide = false;
		addObject(_companyLogo);
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (!_hide) {
			if (_totalTime > 2000) {
				_companyLogo.setDepthRecursive(-30000);
				((GameScreen)getScreen()).showMenu();
				_hide = true;
				_totalTime = 0;
			}
		} else {
			if (_totalTime > 300) {
				getParent().removeObject(this);
			}
		}
	}
	
}
