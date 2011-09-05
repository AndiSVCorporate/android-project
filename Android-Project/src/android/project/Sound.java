package android.project;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Sound {
	
	private MediaPlayer _mp;
	private boolean _isCompleted;
	
	public Sound(Context context,int sound,boolean toLoop){
		_isCompleted = true;
		_mp = MediaPlayer.create(context, sound);
		if(toLoop)
			_mp.setLooping(true);
        _mp.setOnCompletionListener(new OnCompletionListener() {
        	
            @Override
            public void onCompletion(MediaPlayer mp) {
                _isCompleted = true;
            }

        });
	}
	
	public boolean isCompleted(){
		return _isCompleted;
	}
	public MediaPlayer getMp(){
		return _mp;
	}

	public void setNotCompleted() {
		_isCompleted = false;
	}
}
