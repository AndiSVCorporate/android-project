package android.project;

import java.util.Hashtable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapManager {
	
	private Resources _res;
	
	private Hashtable<Integer, Bitmap> _bitmaps;
	private Hashtable<Integer, Positioning> _calibrations;
	
	public BitmapManager(Resources res) {
		
		_res = res;
		_bitmaps = new Hashtable<Integer, Bitmap>();
		_calibrations = new Hashtable<Integer, Positioning>();
		
		Positioning player = new Positioning((-Constants.SCREEN_PLAYER_WIDTH / 2), (-Constants.SCREEN_PLAYER_HEIGHT / 2));
		Positioning screenLogo = new Positioning((-Constants.ASPECT_WIDTH / 2), (-Constants.ASPECT_HEIGHT / 2));
		Positioning playButton = new Positioning(-80, -55);
		//Positioning menuButton = new Positioning(-40, -40);
		Positioning settingsButton = new Positioning(-24, -24);
		Positioning socialButton = new Positioning(-29, -17);
		Positioning quitConfirmButton = new Positioning(-17, -20);
		Positioning sideBird = new Positioning(-374, -480);
		Positioning flagBird = new Positioning(-37.5f, 50);
		
		Positioning bigButtonMiddle = new Positioning(-60, -60);
		
		_calibrations.put(R.drawable.player2, player);
		_calibrations.put(R.drawable.player2pu, player);
		
		_calibrations.put(R.drawable.button_play1, playButton);
		_calibrations.put(R.drawable.button_settings_1, settingsButton);
		_calibrations.put(R.drawable.button_social_1, socialButton);
		_calibrations.put(R.drawable.button_quit2, quitConfirmButton);
		
		_calibrations.put(R.drawable.side_bird, sideBird);
		_calibrations.put(R.drawable.flag_bird, flagBird);
		
		_calibrations.put(R.drawable.button_settings_big, bigButtonMiddle);
		
		_calibrations.put(R.drawable.epicfailgamingstudios, screenLogo);
		_calibrations.put(R.drawable.game_screen, screenLogo);
		
		loadBitmap(R.drawable.player2, Bitmap.Config.ARGB_4444);
		loadBitmap(R.drawable.player2pu, Bitmap.Config.ARGB_4444);
		loadBitmap(R.drawable.epicfailgamingstudios, Bitmap.Config.RGB_565);
		loadBitmap(R.drawable.game_screen, Bitmap.Config.ARGB_4444);
		loadBitmap(R.drawable.button_play1, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.button_quit2, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.button_settings_1, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.button_social_1, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.side_bird, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.button_settings_big, Bitmap.Config.ARGB_8888);
		
		loadBitmap(R.drawable.flag_bird, Bitmap.Config.ARGB_8888);

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
		
		Bitmap bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(_res, bitmapId, opts));
		if (bitmap == null)
			return;
		
		_bitmaps.put(bitmapId, bitmap);
	}
	
	public void unloadBitmap(int bitmapId) {
		_bitmaps.remove(bitmapId);
	}
}
