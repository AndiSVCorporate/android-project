package android.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class CompanyLogoScreen implements Screen {

	private ModelCompanyLogo _logo;
	
	public CompanyLogoScreen() {
		_logo = new ModelCompanyLogo();
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas c) {
		_logo.draw(c);
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
	public void addObject2D(Object2D o) { }

	@Override
	public void removeObject2D(Object2D o) { }

}
