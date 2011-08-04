package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.models.ModelBackground;
import android.project.models.ModelCompanyLogo;
import android.view.MotionEvent;

public class CompanyLogoScreen extends Screen {

	public CompanyLogoScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		
		getWorld().addObject(new ModelBackground(Color.WHITE));
		getWorld().addObject(new ModelCompanyLogo());
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
	public void postInvalidate() {
		
	}

	@Override
	public void calculateThis(long timeDiff) {
		
	}
}
