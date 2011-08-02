package android.project;

import android.graphics.Color;
import android.project.models.ModelBackground;
import android.project.models.ModelCompanyLogo;
import android.view.MotionEvent;

public class CompanyLogoScreen extends Screen {

	private World _world;
	
	public CompanyLogoScreen(CalculateThread calculateThread) {
		super(calculateThread);
		
		_world = new World();
		_world.addObject(new ModelBackground(Color.WHITE));
		_world.addObject(new ModelCompanyLogo());
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
}
