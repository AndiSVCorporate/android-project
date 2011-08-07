package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.models.ModelBackground;
import android.project.models.ModelGameMenu;
import android.project.models.ModelMoveObject;
import android.project.models.ModelSideBird;
import android.view.MotionEvent;

public class GameLogoScreen extends Screen {

	public GameLogoScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		getWorld().addObject(new ModelBackground(0xffcc6600));
		getWorld().addObject(new ModelGameMenu(220, 320));
		getWorld().addObject(new ModelMoveObject(new ModelSideBird(1200, 480), 802, 480, 300));
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
