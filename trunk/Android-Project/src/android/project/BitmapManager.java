package android.project;

import java.util.Hashtable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapManager {
	
	private Resources _res;
	
	private Hashtable<Integer, Bitmap> _bitmaps;
	private Hashtable<Integer, Position> _calibrations;
	
	public BitmapManager(Resources res) {
		
		_res = res;
		_bitmaps = new Hashtable<Integer, Bitmap>();
		_calibrations = new Hashtable<Integer, Position>();
		
		Position player = new Position((-Constants.SCREEN_PLAYER_WIDTH / 2), (-Constants.SCREEN_PLAYER_HEIGHT / 2));
		Position screenLogo = new Position((-Constants.ASPECT_WIDTH / 2), (-Constants.ASPECT_HEIGHT / 2));
		Position playButton = new Position(-80, -55);
		//Positioning menuButton = new Positioning(-40, -40);
		Position settingsButton = new Position(-24, -24);
		Position socialButton = new Position(-29, -17);
		Position quitConfirmButton = new Position(-17, -20);
		Position sideBird = new Position(-374, -480);
		Position flagBird = new Position(-37.5f, -50);
		Position soundOnButton = new Position(-20, -16.5f);
		Position bigButtonMiddle = new Position(-60, -60);
		Position fallingBird = new Position(-35f, -43f);
		
		_calibrations.put(R.drawable.player2, player);
		_calibrations.put(R.drawable.player2pu, player);
		
		_calibrations.put(R.drawable.button_social_big, new Position(-70, -40));
		_calibrations.put(R.drawable.facebook, new Position(-40, -40));
		_calibrations.put(R.drawable.twitter, new Position(-40, -40));
		_calibrations.put(R.drawable.highscore, new Position(-40, -40));
		_calibrations.put(R.drawable.background, new Position(-26.5f, -17.5f));
		_calibrations.put(R.drawable.pause, new Position(-47.5f, -47.5f));
		_calibrations.put(R.drawable.button_play1, playButton);
		_calibrations.put(R.drawable.button_settings_1, settingsButton);
		_calibrations.put(R.drawable.button_social_1, socialButton);
		_calibrations.put(R.drawable.button_quit2, quitConfirmButton);
		_calibrations.put(R.drawable.sound_on2, soundOnButton);
		
		_calibrations.put(R.drawable.side_bird, sideBird);
		_calibrations.put(R.drawable.flag_bird, flagBird);
		
		_calibrations.put(R.drawable.button_settings_big, bigButtonMiddle);
		
		_calibrations.put(R.drawable.model_bird_1_falling, fallingBird);
		
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
		loadBitmap(R.drawable.sound_on2, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.flag_bird, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.model_bird_1_falling, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.building, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.facebook, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.twitter, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.background, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.highscore, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.button_social_big, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.pause, Bitmap.Config.ARGB_8888);

	}
	
	public Bitmap getBitmap(int bitmapId) {
		return _bitmaps.get(bitmapId);
	}
	
	public Position getCalibrationData(int bitmapId) {
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
