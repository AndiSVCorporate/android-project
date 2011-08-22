package android.project.models;

import android.project.Object2D;
import android.project.Utils;
import android.view.MotionEvent;

public class ModelMenuScreen extends Object2D {
	
	private ModelGameMenu _menu;
	private ModelSideBird _sideBird;
	private ModelRect _sky;
	private ModelRect _ground;
	private long _totalTime;
	private boolean _hide;
	
	public ModelMenuScreen() {
		_menu = null;
		_hide = false;
	}
	
	public void show() {
		_menu = new ModelGameMenu(220, 320);
		_sideBird = new ModelSideBird(1200, 480);
		_sky = new ModelRect(800, 240, 0, -240, 0xffadd8c7) {{ setDepth(-20000); }};
		_ground = new ModelRect(800, 240, 0, 480, 0xffcc6600) {{ setDepth(-2000); }};
		
		addObject(_menu);
		addObject(new ModelMoveObject(_sideBird, -400, 0, 300));
		addObject(new ModelMoveObject(_sky, 0, 240, 300));
		addObject(new ModelMoveObject(_ground, 0, -240, 300));
	}
	
	public void hide() {
		_hide = true;
		_totalTime = 0;
		removeObject(_menu);
		ModelScaleObject scaleObject = new ModelScaleObject(_menu, 1, 0.4f, 500);
		ModelMoveObject moveObject = new ModelMoveObject(scaleObject, 525, -270, 500);
		addObject(moveObject);
		freeInnerObject(this, _sideBird);
		_sky.setDepthRecursive(-1);
		_ground.setDepthRecursive(-1);
		
		//addObject(new ModelMoveObject(_menu, -400, 0, 300));
		addObject(new ModelMoveObject(_sideBird, 400, 0, 300));
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		if (_hide == false)
			return;
		if (_totalTime < 1000) {
			_totalTime += timeDiff;
			return;
		}
		
		//freeInnerObject(this, _menu);
		freeInnerObject(this, _sideBird);
		freeInnerObject(this, _sky);
		freeInnerObject(this, _ground);
		//_menu = null;

		_hide = false;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if (_hide == true || _menu == null)
			return true;
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
	
	public void onBackPressed() {
		if (_hide == true || _menu == null)
			return;
    	_menu.onBackPressed();
    }

}
