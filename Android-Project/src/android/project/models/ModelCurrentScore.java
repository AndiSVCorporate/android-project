package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Utils;

public class ModelCurrentScore extends Object2D {
	
	private int _score;
	private int _best;
	private long _timeFromBest;
	public ModelCurrentScore(int best){
		_score=0;
		_best=best;
		_timeFromBest=500;
	}
	
	@Override
	public void drawThis(Canvas c) {
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setTypeface(Utils.getTypeface());
		paint.setTextSize(30);
	    c.drawText("Score: " + _score, 200, 60, paint);	    
	    if(_timeFromBest<500){
	    	paint.setColor(Color.RED);
	    	paint.setTextSize(50);
		    c.drawText("NEW HIGHSCORE!", 100, 200, paint);
	    }	    	
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		if(_timeFromBest<500)
			_timeFromBest+=timeDiff;
		super.calculateThis(timeDiff);
	}
	
	public void addScore(int s){
		_score+=s;
		if(_score>_best){
			_timeFromBest=0;
			_best=Integer.MAX_VALUE;
		}
	}
	public int getScore(){
		return _score;
	}
}
