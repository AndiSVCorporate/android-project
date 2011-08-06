package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.models.ModelBackground;
import android.project.models.ModelBezierCurve;
import android.project.models.ModelButton;
import android.project.models.ModelCompanyLogo;
import android.project.models.ModelFloatingModel;
import android.project.models.ModelGameMenu;
import android.project.models.ModelGameSplash;
import android.project.models.ModelSmoke;
import android.view.MotionEvent;

public class GameLogoScreen extends Screen {

	public GameLogoScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		//getWorld().addObject(new ModelBackground(0xFFA4C639));
		getWorld().addObject(new ModelBackground(Color.WHITE));
		//getWorld().addObject(new ModelGameSplash());
		//getWorld().addObject(new ModelBezierCurve(Color.BLACK));
		getWorld().addObject(new ModelGameMenu(220, 320));
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
