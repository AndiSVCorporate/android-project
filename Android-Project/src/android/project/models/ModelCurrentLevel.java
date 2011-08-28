package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Utils;

public class ModelCurrentLevel extends Object2D {
	
	private int _level;
	private long _timeFromChange;
	public ModelCurrentLevel(){
		_level=0;
		_timeFromChange=0;
	}
	
	@Override
	public void drawThis(Canvas c) {
		Paint paint=new Paint();
		paint.setColor(Color.GRAY);
		paint.setTypeface(Utils.getTypeface());
		paint.setTextSize(20);
	    c.drawText("Level " + (_level+1), 200, 25, paint);
	    if(_timeFromChange<500){
			paint.setColor(Color.WHITE);
	    	paint.setTextSize(40);
			paint.setShadowLayer(20, 5, 5, Color.BLACK);
	    	c.drawText("Level " + (_level+1), 300, 200, paint);
	    }
	}
	@Override
	public void calculateThis(long timeDiff) {
		super.calculateThis(timeDiff);
		if(_timeFromChange<500)
			_timeFromChange+=timeDiff;
	}
	public void levelUp(){
		_level++;
		_timeFromChange=0;
	}
	public int getLevel(){
		return _level;
	}
}
