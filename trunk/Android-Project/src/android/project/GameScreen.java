package android.project;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

public class GameScreen implements Screen {

	private ModelPlayer _player;
	private ModelGameSplash _logo;
	
	private ArrayList<Object2D> _objects;
	private ArrayList<Object2D> _toAdd;
	private ArrayList<Object2D> _toRemove;
	
	public GameScreen() {
		_objects = new ArrayList<Object2D>();
		
		_player = new ModelPlayer();
		_logo = new ModelGameSplash();
		
		_objects.add(_player);
		//_objects.add(_logo);
	}
	
	@Override
	public void draw(Canvas c) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		c.drawRect(0, 0, 800, 480, paint);
		
		for (Object2D object : _objects) {
			object.draw(c);
		}
		
	}
	
	public void calculate() {
		_objects.removeAll(_toRemove);
		_objects.addAll(_toAdd);
		
		_toRemove.clear();
		_toAdd.clear();
		
		for (Object2D object : _objects) {
			object.calculate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		int actionIndex = event.getActionIndex();
		
		float[] point = new float[] {event.getX(actionIndex), event.getY(actionIndex)};
		
		//Log.d("click0", "x = " + point[0] + "; y = " + point[1]);
		Utils.getInverseCanvasCalibrationMatrix().mapPoints(point);
		
		float x = point[0];
		float y = point[1];
		
		//Log.d("player", "x = " + _player.getX() + "; y = " + _player.getY());
		
		Log.d("check", "index " + actionIndex);
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
			
			Log.d("click", "x = " + x + "; y = " + y);
			Log.d("count", event.getPointerCount() + "");
			_player.move(x, y);
		}
		return true;
	}

	@Override
	public int getBorderColor() {
		return Color.BLACK;
	}
	
	@Override
	public void postInvalidate() {
		//v.postInvalidate(_player.getX() - Constants.SCREEN_PLAYER_WIDTH / 2,
			//	_player.getY() - Constants.SCREEN_PLAYER_HEIGHT / 2,
				//_player.getX() + Constants.SCREEN_PLAYER_WIDTH / 2,
				//_player.getY() + Constants.SCREEN_PLAYER_HEIGHT / 2);
	}

	@Override
	public void addObject2D(Object2D o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObject2D(Object2D o) {
		// TODO Auto-generated method stub
		
	}
}
