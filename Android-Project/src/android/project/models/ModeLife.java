package android.project.models;

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
			Object2DBitmap cur=new Object2DBitmap(R.drawable.heart);
			cur.scale((float) 0.5);
			addObject(cur);
			cur.setX(_pos);
			_pos-=45;
		}
	}

	public void addLife(){
		if(_life==3)
			return;
		_life++;
		Object2DBitmap cur=new Object2DBitmap(R.drawable.heart);
		cur.scale((float) 0.5);
		addObject(cur);
		cur.setX(_pos);
		_pos-=45;
	}
	public void fail(){
		Utils.vibrate(300);
		if(getObjects().size()==0)
			return;
		getObjects().remove(getObjects().size()-1);
		_life--;
		_pos+=45;
	}
	public boolean isAlive(){
		return _life>0;
	}
}
