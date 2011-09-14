package android.project.models;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Bounds;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Score;
import android.project.Utils;
import android.project.bounds.BoundsCircle;
import android.util.Log;
import android.view.MotionEvent;

public class ModelHighscore extends Object2D {
	private Score[] _scores;
	private ModelRoundRect _background;
	private ModelText _title;
	private ModelText _tableTitle;
	private ModelText[] _scoresText;
	private long _totlaTime;
	private boolean _show=false;
	private final long OPEN_TIME=500;
	private final long SCORES_START_TIME=500;
	private final long FADE_TIME=200;
	private Line[] _lines;
	private ModelFacebookButton[] _fbButtons;
	private Object2D[] _buttons;

	public ModelHighscore() {
//		_scores=Utils.getScores();
		_lines=new Line[3];
		Paint p=new Paint();
		p.setColor(Color.BLUE);
		p.setAlpha(200);
		_background=new ModelRoundRect(400, 0,0,0,p);
		_background.setDepth(40000);
		addObject(_background);
		_title=new ModelText("Highscores",Color.BLACK,35);
		_title.setY(80);
		_title.setX(30);
		_title.setDepth(45000);

		_tableTitle=new ModelText("  LVL  SCORE  POST",Color.BLACK,20);
		_tableTitle.setY(120);
		_tableTitle.setX(20);
		_tableTitle.setDepth(45000);
		_title.setAlpha(0);
		_tableTitle.setAlpha(0);

		addObject(_title);
		addObject(_tableTitle);
	}
	public boolean is_show() {
		return _show;
	}
	public void show(){
		_totlaTime=0;
		_show=true;
		_scores=Utils.getScores();
		int[] colors={0xffeaba52,Color.GRAY,0xffcc622e};
		for(int i=0;i<3;i++){
			_lines[i]=new Line(_scores[i], i, colors[i]);
			_lines[i].setY(80+(90*(i+1)));
			_lines[i].setX(20);
			addObject(_lines[i]);
//			_lines[i].show();
		}
		_buttons=getButtons();		
	}

	@Override
	public void calculateThis(long timeDiff) {
		if(_show){
			_totlaTime+=timeDiff;
			if(_background.getHeight()<420)
				_background.setHeight(420*_totlaTime/OPEN_TIME);
			if(_background.getHeight()>=420){		
			}
			if(_totlaTime>OPEN_TIME && _totlaTime<OPEN_TIME+FADE_TIME){
				_title.setAlpha((int) (255*(_totlaTime-OPEN_TIME)/FADE_TIME));
				_tableTitle.setAlpha((int) (255*(_totlaTime-OPEN_TIME)/FADE_TIME));
			}
			if(_totlaTime>OPEN_TIME+FADE_TIME && !_lines[0]._showLine)
				_lines[0].show();
			if(_totlaTime>OPEN_TIME+2*FADE_TIME && !_lines[1]._showLine)
				_lines[1].show();
			if(_totlaTime>OPEN_TIME+3*FADE_TIME && !_lines[2]._showLine)
				_lines[2].show();
			
		}
		else if(_lines[0]!=null){
			_totlaTime+=timeDiff;
			if(_lines[2]._showLine)
				_lines[2].hide();
			if(_totlaTime>FADE_TIME && _lines[1]._showLine)
				_lines[1].hide();
			if(_totlaTime>2*FADE_TIME && _lines[0]._showLine)
				_lines[0].hide();
			float ratio=(float)(_totlaTime-3*FADE_TIME)/FADE_TIME;
			ratio=Math.min(1, ratio);
			ratio=Math.max(0, ratio);
			if(_totlaTime>3*FADE_TIME && _title.getPaint().getAlpha()>0){
				_title.setAlpha((int) (255-255*ratio));
				_tableTitle.setAlpha((int) (255-255*ratio));
			}
			if(_totlaTime>4*FADE_TIME && _background.getHeight()>0)
				_background.setHeight(420-420*(_totlaTime-4*FADE_TIME)/OPEN_TIME);

		}
	}

	public void hide(){
		_show=false;
		_totlaTime=0;
	}
	
	private int _pressingButton;
	public void press(float x, float y) {
		Log.d("POST",":::");

		if(!_show)
			return;
		for (int i = 0; i < _buttons.length; ++i)
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
		Utils.postHighscore(_scores[i].get_score());
		
	}
	private void postToOpenfeint(int i){
		Utils.postToOpenFeint(_scores[i].get_score());
	}
	
	private class Line extends Object2D {
		private Score _s;
		private ModelText _text;
		private ModelFacebookSubmitButtonSmall  _fb;
		private Object2DBitmap _of;
		private static final long SHOW_LENGTH=200;
		private boolean _showLine;
		private long _myTotalTime;
		private Line(Score s, int i, int color){
			_s=s;
			_text=new ModelText((i+1)+". "+_s.get_level()+"   "+_s.get_score(),color,20);
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
			_showLine=false;
		}
		public void show(){
			_showLine=true;
			_myTotalTime=0;
		}
		public void hide(){
			_showLine=false;
			_myTotalTime=0;
		}
		@Override
		public void calculateThis(long timeDiff) {
			_myTotalTime+=timeDiff;
			float ratio=(float)_myTotalTime/SHOW_LENGTH;
			ratio=Math.min(1, ratio);
			ratio=Math.max(0, ratio);
			if(_showLine){
				if(_text.getPaint().getAlpha()<255){
					_text.setAlpha((int) (255*ratio));
					_fb.setAlpha((int) (255*ratio));
					_of.setAlpha((int) (255*ratio));
				}
			}
			else{
				if(_text.getPaint().getAlpha()>0){
					_text.setAlpha((int) (255-255*ratio));
					_fb.setAlpha((int) (255-255*ratio));
					_of.setAlpha((int) (255-255*ratio));
				}
				
			}
		}
	}
}
