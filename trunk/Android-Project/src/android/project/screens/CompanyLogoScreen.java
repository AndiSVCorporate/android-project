package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.SoundManager;
import android.project.CanvasRenderer.ScreenEnum;
import android.project.models.ModelBackground;
import android.project.models.ModelCompanyLogo;
import android.project.models.ModelCover;
import android.view.MotionEvent;

public class CompanyLogoScreen extends Screen {

	private long _totalTime;
	private ModelCover _cover;
	
	public CompanyLogoScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		
		_cover = new ModelCover(0x00000000);
		
		getWorld().addObject(new ModelBackground(Color.WHITE));
		getWorld().addObject(new ModelCompanyLogo());
		getWorld().addObject(_cover);
		
		_totalTime = 0;
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBorderColor() {
		return Color.WHITE;
	}

	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime > 3200)
			getCanvasRenderer().setActiveScreen(ScreenEnum.GAME_MAIN_MENU);
		else if (_totalTime > 3000) {
			long change = _totalTime - 3000;
			int c = (int) (255 * ((float) change / 200));
			_cover.setColor(Color.argb(c, 0, 0, 0));
		}
	}
}
