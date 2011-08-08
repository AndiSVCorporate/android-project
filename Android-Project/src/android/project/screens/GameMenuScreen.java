package android.project.screens;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Screen;
import android.project.Utils;
import android.project.models.ModelBackground;
import android.project.models.ModelFlagBird;
import android.project.models.ModelGameMenu;
import android.project.models.ModelMoveObject;
import android.project.models.ModelRect;
import android.project.models.ModelSideBird;
import android.view.MotionEvent;

public class GameMenuScreen extends Screen {
	
	ModelGameMenu _menu;

	public GameMenuScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		_menu = new ModelGameMenu(220, 320);
		getWorld().addObject(new ModelBackground(0xffadd8c7));
		getWorld().addObject(_menu);
		getWorld().addObject(new ModelRect(800, 240, 0, 240, 0xffcc6600) {{ setDepth(-2000); }});
		//getWorld().addObject(new ModelFlagBird());
		getWorld().addObject(new ModelMoveObject(new ModelSideBird(1200, 480), 802, 480, 300));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
		
		float[] point = new float[] {event.getX(0), event.getY(0)};
		
		//Log.d("click0", "x = " + point[0] + "; y = " + point[1]);
		Utils.getInverseCanvasCalibrationMatrix().mapPoints(point);
		
		float x = point[0];
		float y = point[1];
		
		if (action == MotionEvent.ACTION_DOWN) {
			_menu.press(x, y);
		} else if (action == MotionEvent.ACTION_UP) {
			_menu.release(x, y);
		} else if (action == MotionEvent.ACTION_MOVE) {
			_menu.move(x, y);
		}
		return true;
	}

	long totalTime = 0;
	
	@Override
	public void calculateThis(long timeDiff) {
		if (totalTime < 10000)
			totalTime += timeDiff;
	}

	@Override
	public int getBorderColor() {
		return Color.BLACK;
	}

}
