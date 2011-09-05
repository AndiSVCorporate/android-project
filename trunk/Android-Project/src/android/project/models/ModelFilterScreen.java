package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;

public class ModelFilterScreen extends Object2D{

	private int _color;
	private int _opacity;
	private boolean _fadeIn;
	private ModelRect _rect;
	public ModelFilterScreen(int color){
		_color=color;
		_fadeIn=false;
		_opacity=0;
		_rect=new ModelRect(800, 480,_color);
		addObject(_rect);
		_rect.setDepth(40);
	}
	
	@Override
	public void calculateThis(long timeDiff) {
		super.calculateThis(timeDiff);
		timeDiff/=10;
		if(_fadeIn && _opacity<128)
			_opacity=(int) Math.min(_opacity+timeDiff,128);
		if(!_fadeIn && _opacity>0)
			_opacity=(int) Math.max(_opacity-timeDiff,0);
		_rect.getPaint().setAlpha(_opacity);
	}

	public void fadeIn(){
		_fadeIn=true;
	}
	public void fadeOut(){
		_fadeIn=false;
	}
	public boolean exist(){
		return _opacity>0;
	}
}
