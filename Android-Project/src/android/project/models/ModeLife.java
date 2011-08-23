package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.Utils;

public class ModeLife extends Object2D {

	private int _life;
	private float _pos;
	public ModeLife(int init){
		_life=init;
		_pos=0;
		for(int i=0;i<init;i++){
			Object2DBitmap cur=new Object2DBitmap(R.drawable.flag_bird);
			addObject(cur);
			cur.setX(_pos);
			_pos+=70;
		}
	}

	public void addLife(){
		_life++;
		Object2DBitmap cur=new Object2DBitmap(R.drawable.flag_bird);
		addObject(cur);
		cur.setX(_pos);
		_pos+=70;
	}
	public void fail(){
		getObjects().remove(getObjects().size()-1);
		_life--;
	}
	public boolean isAlive(){
		return _life>0;
	}
}
