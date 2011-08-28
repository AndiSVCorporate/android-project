package android.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openfeint.api.OpenFeint;
import com.openfeint.api.OpenFeintDelegate;
import com.openfeint.api.OpenFeintSettings;
import com.openfeint.api.resource.Achievement;
import com.openfeint.api.resource.Leaderboard;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
    	_canvasRenderer.onPause();
    	super.onPause();
    }
    
    @Override
    protected void onStop() {
    	_canvasRenderer.onPause();
    	super.onStop();
    }
    
    @Override
    protected void onResume() {
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