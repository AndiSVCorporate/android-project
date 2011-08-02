package android.project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class Run extends Activity {
	
	CanvasRenderer _canvasRenderer;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rWidth = display.getWidth();
        int rHeight = display.getHeight();
        
        _canvasRenderer = new CanvasRenderer(this, rWidth, rHeight);
        
        setContentView(_canvasRenderer);
    }
    
    @Override
    protected void onPause() {
    	_canvasRenderer.onPause();
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	_canvasRenderer.onResume();
    	super.onResume();
    }
}