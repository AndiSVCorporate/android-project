package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;
import android.project.Utils;

public class ModelScoreFly extends Object2D {
	private static final int[] colors={Color.CYAN,Color.LTGRAY,Color.YELLOW,Color.MAGENTA};
	private int _score;
	private float _y;
	private float _x;
	private int _color;
	public ModelScoreFly(int score, float x){
		_score=score;
		_y=450;
		_x=x-40;
		_color=(int) (Math.random()*4);
	}
	
	@Override
	public void drawThis(Canvas c) {
		if(isDone()){
			freeInnerObject(getParent(), this);
			return;
		}
		if(_score<=0)
			return;
		Paint paint=new Paint();
		paint.setColor(colors[_color]);
		paint.setTypeface(Utils.getTypeface());
		paint.setTextSize(30);
	    c.drawText("+" + _score, _x, _y, paint);
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		super.calculateThis(timeDiff);
		_y-=0.5*timeDiff;
	}
	public boolean isDone(){
		return _y<100;
	}
}
