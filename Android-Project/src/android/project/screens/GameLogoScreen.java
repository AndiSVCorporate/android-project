package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.models.ModelGameSplash;
import android.view.MotionEvent;

public class GameLogoScreen extends Screen {

	public GameLogoScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		getWorld().addObject(new ModelGameSplash());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void calculateThis(long timeDiff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBorderColor() {
		return Color.BLACK;
	}

	@Override
	public void postInvalidate() { }

}
