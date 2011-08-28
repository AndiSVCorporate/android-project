package android.project.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Scheduler;
import android.project.Screen;
import android.project.Utils;
import android.project.models.ModelBackground;
import android.project.models.ModelJumpingObject;
import android.project.models.ModelPlayer;
import android.project.models.ModelSmoke;
import android.util.Log;
import android.view.MotionEvent;

public class GamePlayScreen extends Screen {

	private ModelPlayer _player;
	private Scheduler _scheduler;
	private List<Object2D> _balls;
	
	
	public GamePlayScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		_player = new ModelPlayer();
		_scheduler = new Scheduler(500);
		getWorld().addObject(new ModelBackground(0xffffffff));
		//getWorld().addObject(new ModelBackground(0xffcc6600));
		getWorld().addObject(_player);
		getWorld().addObject(new Object2DBitmap(R.drawable.building) {{setDepth(-500);}});
		//getWorld().addObject(new ModelBezierCurve(Color.GREEN));
		_balls = new ArrayList<Object2D>();
		for (int i = 0; i < 3; ++i)
			_player.addObject(new ModelSmoke(0, 0));
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
	public void calculateThis(long timeDiff) {
		_scheduler.calculate(timeDiff);
		
		if (_scheduler.getNext() != 0) {
			float v = _scheduler.getNext();
			long tFall = (long) (100 * 1000 / v);
			Object2D ball = new Object2DBitmap(R.drawable.model_bird_1_falling);
			ball.setX(150);
			ball.setY(50);
			getWorld().addObject(new ModelJumpingObject(ball, tFall, 100, 440 - 50, tFall));
			_balls.add(ball);
		}
		
		for(Object2D ball:_balls){
			ModelJumpingObject jmp = (ModelJumpingObject) ball.getParent();
			if(jmp == null)
				continue;
			if(!jmp.isFinished())
				continue;
			if(Math.abs(ball.getRealX() - _player.getRealX()) < 80){
				jmp.finalizePosition();
				jmp.freeInnerObject(ball);
				getWorld().removeObject(jmp);
				getWorld().addObject(new ModelJumpingObject(ball, jmp.getTFall(), 100, 440 - 50, jmp.getTime() - 2 * jmp.getTFall()));				
			} else {
				jmp.freeInnerObject(ball);
				getWorld().removeObject(jmp);
			}
		}
	}
}
