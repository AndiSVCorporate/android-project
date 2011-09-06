package android.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class Run extends Activity {
			
	CanvasRenderer _canvasRenderer;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setActivity(this);
        SoundManager.initialize();
        
        getWindow().setFormat(PixelFormat.RGBA_8888);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rWidth = display.getWidth();
        int rHeight = display.getHeight();
        
        _canvasRenderer = new CanvasRenderer(this, rWidth, rHeight);    
        setContentView(_canvasRenderer);
    
    }
    
    private long _backPressedTime = 0;
    
    @Override
    public void onBackPressed() {
    	long time = SystemClock.elapsedRealtime();
    	if (time < _backPressedTime + 300)    		
    		super.onBackPressed();
    	_backPressedTime = time;
    	_canvasRenderer.onBackPressed();
    	
    }
    
    @Override
    protected void onPause() {
    	SoundManager.pause();
    	_canvasRenderer.onPause();
    	super.onPause();
    }
    
    @Override
    protected void onStop() {
    	SoundManager.pause();
    	_canvasRenderer.onPause();
    	super.onStop();
    }
    
    @Override
    protected void onResume() {
    	SoundManager.resume();
    	_canvasRenderer.onResume();
    	super.onResume();
    }
   
    @Override
    protected void onRestart() {
    	_canvasRenderer.onResume();
    	super.onRestart();
    } 
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
}