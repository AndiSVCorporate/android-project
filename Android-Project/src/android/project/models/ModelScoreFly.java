package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Utils;

public class ModelScoreFly extends Object2D {
	private static final int[] colors={Color.CYAN,Color.YELLOW,Color.MAGENTA};
	private int _score;
	private float _y;
	private float _x;
	private int _color;
	boolean _show;
	private long _totalTime;
	public ModelScoreFly(int score, float x){
		_score=score;
		_y=450;
		_x=x-40;
		_color=(int) (Math.random()*3);
		_show=true;
	}
	
	@Override
	public void drawThis(Canvas c) {
		_show=!_show;
//		if(!_show)
//			return;
		if(isDone()){
			getParent().removeObject(this);
			return;
		}
		if(_score<=0)
			return;
		Paint paint=new Paint();
		paint.setColor(colors[_color]);
		paint.setTypeface(Utils.getTypeface());
		paint.setTextSize(20+(Math.min((int)(_score/10),15)));
		float ratio=((float)_totalTime/800);
		ratio=Math.max(ratio, 0);
		ratio=Math.min(ratio, 255);
		paint.setAlpha((int) (255-255*ratio));
		c.drawText("+" + _score, _x, _y, paint);
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		super.calculateThis(timeDiff);
		_totalTime+=timeDiff;
		_y-=0.5*timeDiff;
		
	}
	public boolean isDone(){
		return _y<100;
	}
}
