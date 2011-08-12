package android.project.screens;

import java.util.Collection;
import java.util.Vector;

import android.graphics.Color;
import android.graphics.Paint;
import android.project.CalculateThread;
import android.project.CanvasRenderer;
import android.project.Object2D;
import android.project.Screen;
import android.project.Utils;
import android.project.models.ModelBackground;
import android.project.models.ModelCircle;
import android.project.models.ModelJumpingObject;
import android.project.models.ModelThrowingObject;
import android.project.models.ModelPlayer;
import android.project.models.ModelSmoke;
import android.util.Log;
import android.view.MotionEvent;

public class GameScreen extends Screen {

	private ModelPlayer _player;
	private Collection<Object2D> _balls;
	public GameScreen(CalculateThread calculateThread, CanvasRenderer canvasRenderer) {
		super(calculateThread, canvasRenderer);
		_player = new ModelPlayer();
		getWorld().addObject(new ModelBackground(0xffffffff));
		//getWorld().addObject(new ModelBackground(0xffcc6600));
		getWorld().addObject(_player);
		//getWorld().addObject(new ModelBezierCurve(Color.GREEN));
		_balls=new Vector<Object2D>();
		for (int i = 0; i < 3; ++i)
			_player.addObject(new ModelSmoke(0, 0));
		
		Object2D ball1=new ModelCircle(30, 150, 50, 0xff0000ff);
		getWorld().addObject(new ModelJumpingObject(ball1, 1000, 100, 440 - 50, 1000));
		_balls.add(ball1);
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
		for(Object2D ball:_balls){
			ModelJumpingObject jmp=(ModelJumpingObject) ball.getParent();
			if(jmp==null)
				continue;
			if(!jmp.isFinished())
				continue;
			jmp.freeInnerObject(ball);
			getWorld().removeObject(jmp);
			if(Math.abs(ball.getRealX()-_player.getRealX())<20){
				Log.d("wow", ball.getRealY()+"");
				getWorld().addObject(new ModelJumpingObject(ball, 1000, 100, 440 - 50, jmp.getTime()-2000));				
			}
		}
	}
}
