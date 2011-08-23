package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;
import android.project.Utils;

public class ModelCurrentScore extends Object2D {
	
	private int _score;
	
	public ModelCurrentScore(){
		_score=0;
	}
	
	@Override
	public void drawThis(Canvas c) {
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setTypeface(Utils.getTypeface());
		paint.setTextSize(30);
	    c.drawText("Score: " + _score, 200, 35, paint);
	}
	public void addScore(int s){
		_score+=s;
	}
}
