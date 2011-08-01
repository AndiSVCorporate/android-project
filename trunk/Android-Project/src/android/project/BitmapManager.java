package android.project;

import java.util.Hashtable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class BitmapManager {
	
	private Resources _res;
	
	private Hashtable<Integer, Bitmap> _bitmaps;
	private Hashtable<Integer, Positioning> _calibrations;
	
	public BitmapManager(Resources res) {
		
		_res = res;
		_bitmaps = new Hashtable<Integer, Bitmap>();
		_calibrations = new Hashtable<Integer, Positioning>();
		
		Positioning player = new Positioning((-Constants.SCREEN_PLAYER_WIDTH / 2), (-Constants.SCREEN_PLAYER_HEIGHT / 2), 1f, 1f, 0.0f);
		Positioning screenLogo = new Positioning((-Constants.ASPECT_WIDTH / 2), (-Constants.ASPECT_HEIGHT / 2), 1f, 1f, 0.0f);
		
		_calibrations.put(R.drawable.player, player);
		_calibrations.put(R.drawable.player2, player);
		_calibrations.put(R.drawable.player2pu, player);
		
		_calibrations.put(R.drawable.epicfailgamingstudios, screenLogo);
		_calibrations.put(R.drawable.game_screen, screenLogo);
		
		loadBitmap(R.drawable.player, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.player2, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.player2pu, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.epicfailgamingstudios, Bitmap.Config.RGB_565);
		loadBitmap(R.drawable.game_screen, Bitmap.Config.ARGB_8888);
		
	}
	
	public Bitmap getBitmap(int bitmapId) {
		return _bitmaps.get(bitmapId);
	}
	
	public Positioning getCalibrationData(int bitmapId) {
		return _calibrations.get(bitmapId);
	}
	
	public void loadBitmap(int bitmapId, Bitmap.Config config) {
		Options opts = new Options();
		opts.inPreferredConfig = config;
		
		Bitmap bitmap = BitmapFactory.decodeResource(_res, bitmapId, opts);
		if (bitmap == null)
			return;
		
		_bitmaps.put(bitmapId, bitmap);
	}
	
	public void unloadBitmap(int bitmapId) {
		_bitmaps.remove(bitmapId);
	}
}
