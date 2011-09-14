package android.project.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.project.Bounds;
import android.project.Constants;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Score;
import android.project.Utils;
import android.project.bounds.BoundsCircle;
import android.util.Log;

public class ModelHighscore extends Object2D {
	private ModelRoundRect _background;
	private ModelText _title;
	private ModelText _tableTitle;
	private long _totalTime;
	private boolean _show=false;
	private static final long OPEN_TIME = 500;
	
	private Line[] _lines;

	private Object2D[] _buttons;
	
	public ModelHighscore() {
		//		_scores=Utils.getScores();
		_totalTime = 0;
		_background = new ModelRoundRect(400, 0, 0, 0, new Paint());
		_background.setDepth(40000);
		addObject(_background);
		_title=new ModelText("Highscores",Color.BLACK,35);
		_title.setY(80);
		_title.setX(30);
		_title.setDepth(45000);

		_tableTitle=new ModelText("  LVL  SCORE  POST", Color.BLACK, 20);
		_tableTitle.setY(120);
		_tableTitle.setX(20);
		_tableTitle.setDepth(45000);
		_title.setAlpha(0);
		_tableTitle.setAlpha(0);
		
		_lines = new Line[3];
		
		int[] colors = new int[] { Constants.COLOR_GOLD, Constants.COLOR_SILVER, Constants.COLOR_BRONZE };
		
		for(int i=0;i<3;i++){
			_lines[i]=new Line(i, colors[i]);
			_lines[i].setY(80+(90*(i+1)));
			_lines[i].setX(20);
			addObject(_lines[i]);
		}

		_buttons = getButtons();
		
		addObject(_title);
		addObject(_tableTitle);
	}
	public boolean is_show() {
		return _show;
	}
	public void show() {
		_show = true;
	}

	@Override
	public void calculateThis(long timeDiff) {
		if (_show)
			_totalTime +=  timeDiff;
		else
			_totalTime -= timeDiff;
		_totalTime = Math.min(_totalTime, OPEN_TIME);
		_totalTime = Math.max(_totalTime, 0);
		
		
		float heightFloat = progress(_totalTime, 0, 500);

		_background.setHeight(420 * heightFloat);
	
		float titleOpenFloat = progress(_totalTime, 100, 200);
		
		_title.setAlpha((int) (255 * titleOpenFloat));
		_tableTitle.setAlpha((int) (255 * titleOpenFloat));
		
		_lines[0].setAlpha(progress(_totalTime, 200, 300));
		_lines[1].setAlpha(progress(_totalTime, 300, 400));
		_lines[2].setAlpha(progress(_totalTime, 400, 500));
		
	}

	public void hide(){
		_show = false;
	}
	
	private float progress(long totalTime, long startTime, long endTime) {
		if (totalTime < startTime)
			return 0;
		if (totalTime > endTime)
			return 1;
		return (float) (totalTime - startTime) / (endTime - startTime);
	}

	private int _pressingButton;
	public void press(float x, float y) {
		Log.d("POST",":::");

		if(!_show)
			return;
		
		for (int i = 0; i < _lines.length; ++i)
			if (_buttons[i].isPointInside(x, y)) {
				_pressingButton = i;
				_buttons[i].scale(1.2f);
				return;
			}
		_pressingButton = -1;
	}

	public void move(float x, float y) {
		if(!_show)
			return;
		if (_pressingButton == -1)
			return;
		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton)
					return;
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;

	}

	public void release(float x, float y) {
		Log.d("POST","::");

		if(!_show)
			return;
		if (_pressingButton == -1)
			return;

		for (int i = 0; i < _buttons.length; ++i)
			if (_buttons[i].isPointInside(x, y))
				if (i == _pressingButton) {
					if(i<3)
						postToFacebook(i);
					else
						postToOpenfeint(i-3);
				}
		_buttons[_pressingButton].scale((1/1.2f));
		_pressingButton = -1;
	}

	public Object2D[] getButtons(){
		Object2D[] ret=new Object2D[6];
		for(int i=0;i<_lines.length;i++){
			ret[i]=_lines[i]._fb;
			ret[i+_lines.length]=_lines[i]._of;
		}
		return ret;
	}

	private void postToFacebook(int i){
		Log.d("POST",":"+i+":");
		Utils.postHighscore(Utils.getScores()[i].get_score());

	}
	private void postToOpenfeint(int i){
		Utils.postToOpenFeint(Utils.getScores()[i].get_score());
	}

	private class Line extends Object2D{
		
		private ModelText _text;
		private ModelFacebookSubmitButtonSmall  _fb;
		private Object2DBitmap _of;
		private int _i;
		private Line(int i, int color){
			_i = i;
			_text= new ModelText("" , color, 20);
			_text.setX(0);
			_text.setDepth(45000);
			_fb=new ModelFacebookSubmitButtonSmall();
			_of=new Object2DBitmap(R.drawable.openfeint2);
			_fb.setX(300);
			_of.setX(350);
			_of.setDepth(45000);
			Bounds b1=new BoundsCircle(40);
			Bounds b2=new BoundsCircle(40);
			_fb.setBounds(b1);
			_of.setBounds(b2);
			_fb.scale(0.5f);
			_of.scale(0.5f);
			_of.setAlpha(0);
			_fb.setAlpha(0);
			_text.setAlpha(0);
			addObject(_text);
			addObject(_fb);
			addObject(_of);
		}
		
		public void setAlpha(float f) {
			_text.setAlpha((int) (255 * f));
			_fb.setAlpha((int) (255 * f));
			_of.setAlpha((int) (255 * f));	
		}
		
		@Override
		public void calculateThis(long timeDiff) {
			Score s = Utils.getScores()[_i];
			_text.setText((_i+1)+". "+s.get_level()+"   "+s.get_score());
		}
	}
}
