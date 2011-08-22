package android.project.models;

import java.util.LinkedList;
import android.project.Object2D;
import android.util.Log;

public class ModelSkylineNear extends Object2D {
	
	private static long TIME_WAIT = 0;
	private static long TIME_SHOW = 350;
	
	private long _totalTime;
	private LinkedList<Object2D> _skylines;
	
	
	public ModelSkylineNear() {
		_totalTime = 0;
		_skylines = new LinkedList<Object2D>();
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime < TIME_WAIT)
			return;
		show();
		if (_totalTime < TIME_WAIT + TIME_SHOW)
			return;
		startMove();
		float firstX = _skylines.getFirst().getRealX();
		if (firstX >= 0) {
			Object2D skyline = _skylines.removeLast();
			freeInnerObject(this, skyline);
			
			Object2D newSkyline = new ModelSkylineChunkNear();
			newSkyline.setX(firstX - 210);
			newSkyline.setY(skyline.getY());
			newSkyline.setDepthRecursive(-3000);
			Object2D moveWrapper = new ModelMoveObject(newSkyline, 1010, 0, 60000);
			
			_skylines.addFirst(newSkyline);
			addObject(moveWrapper);
		}
	}

	
	private boolean _show= false;
	private void show() {
		if (_show)
			return;
		_show = true;
		Object2D rect = new ModelRect(800, 50, 0, 340, 0xffb0b0ff) {{ setDepth(-3000); }};
		Object2D rectMove = new ModelMoveObject(rect, 0, -150, 300);
		addObject(rectMove);
		for (int x = -210; x < 1010; x += 210) {
			Object2D skyline = new ModelSkylineChunkNear();
			skyline.setX(x);
			skyline.setY(340);
			skyline.setDepthRecursive(-3000);
			_skylines.add(skyline);
			Object2D moveWrapper = new ModelMoveObject(skyline, 0, -150, 300);
			addObject(moveWrapper);
		}
		
	}
	
	private boolean _startMove= false;
	private void startMove() {
		if (_startMove)
			return;
		_startMove = true;
		LinkedList<Object2D> newSkylines = new LinkedList<Object2D>();
		for (Object2D skyline : _skylines) {
			freeInnerObject(this, skyline);
			Object2D newSkyline = new ModelSkylineChunkNear();
			newSkyline.setX(skyline.getX());
			newSkyline.setY(skyline.getY());
			Log.d("y", "" + skyline.getY());
			newSkyline.setDepthRecursive(-3000);
			Object2D moveWrapper = new ModelMoveObject(newSkyline, 1010, 0, 60000);
			addObject(moveWrapper);
			newSkylines.add(newSkyline);
		}
		_skylines = newSkylines;
	}
	
}
