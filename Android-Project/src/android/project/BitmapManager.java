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
		Position fallingBird = new Position(-26.5f, -32.5f);
		
		Position heart = new Position(-32f, -32f);
		
		_calibrations.put(R.drawable.player2, player);
		_calibrations.put(R.drawable.player2pu, player);
		_calibrations.put(R.drawable.player2catch, player);
		
		_calibrations.put(R.drawable.button_social_big, new Position(-70, -40));
		_calibrations.put(R.drawable.facebook, new Position(-40, -40));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21f));

		_calibrations.put(R.drawable.openfeint, new Position(-40, -40));		
		_calibrations.put(R.drawable.replay, new Position(-86, -86));
		_calibrations.put(R.drawable.facebook, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.openfeint, new Position(-21.5f, -22.5f));		
		_calibrations.put(R.drawable.replay, new Position(-86, -86));
		_calibrations.put(R.drawable.facebook, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.openfeint, new Position(-21.5f, -22.5f));		
		_calibrations.put(R.drawable.replay, new Position(-86, -86));
		_calibrations.put(R.drawable.facebook, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.openfeint, new Position(-21.5f, -22.5f));		
		_calibrations.put(R.drawable.replay, new Position(-86, -86));
		_calibrations.put(R.drawable.facebook, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.openfeint, new Position(-21.5f, -22.5f));		
		_calibrations.put(R.drawable.replay, new Position(-86, -86));
		_calibrations.put(R.drawable.facebook, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.openfeint, new Position(-21.5f, -22.5f));		
		_calibrations.put(R.drawable.replay, new Position(-86, -86));
		_calibrations.put(R.drawable.facebook, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.facebook_green, new Position(-20.5f, -21));
		_calibrations.put(R.drawable.openfeint, new Position(-21.5f, -22.5f));		
		_calibrations.put(R.drawable.openfeint2, new Position(-40, -40));		
		_calibrations.put(R.drawable.twitter, new Position(-40, -40));
		_calibrations.put(R.drawable.highscores, new Position(-20.5f, -22.5f));
		_calibrations.put(R.drawable.background, new Position(-26.5f, -17.5f));
		_calibrations.put(R.drawable.pause, new Position(-50, -50f));
		_calibrations.put(R.drawable.stop, new Position(-15f, -14.5f));
		_calibrations.put(R.drawable.button_play1, playButton);
		_calibrations.put(R.drawable.button_settings_1, settingsButton);
		_calibrations.put(R.drawable.button_social_1, socialButton);
		_calibrations.put(R.drawable.button_quit2, quitConfirmButton);
		_calibrations.put(R.drawable.sound_on2, soundOnButton);
		_calibrations.put(R.drawable.vibe, new Position(-23,-22));
		_calibrations.put(R.drawable.side_bird, sideBird);
		_calibrations.put(R.drawable.flag_bird, flagBird);
		_calibrations.put(R.drawable.heart, heart);
		
		_calibrations.put(R.drawable.button_settings_big, bigButtonMiddle);
		_calibrations.put(R.drawable.resume, new Position(-60, -50));
		
		_calibrations.put(R.drawable.model_bird_1_falling, fallingBird);
		_calibrations.put(R.drawable.model_bird_2_falling, fallingBird);

		
		_calibrations.put(R.drawable.poison, new Position(-27.5f,-40f));
		_calibrations.put(R.drawable.model_bird_1_falling, new Position(-26.5f,-32.5f));
		_calibrations.put(R.drawable.ladybird, new Position(-26.5f,-26f));
		_calibrations.put(R.drawable.airbird, new Position(-26.5f,-27.5f));
		_calibrations.put(R.drawable.icebird, new Position(-26.5f,-28.5f));
		_calibrations.put(R.drawable.burnedbird, new Position(-26.5f,-26.5f));
		_calibrations.put(R.drawable.colorbird, new Position(-26.5f,-39.5f));
		
		_calibrations.put(R.drawable.epicfailgamingstudios, screenLogo);
		_calibrations.put(R.drawable.game_screen, screenLogo);

		loadBitmap(R.drawable.heart, Bitmap.Config.ARGB_4444);
		loadBitmap(R.drawable.player2, Bitmap.Config.ARGB_4444);
		loadBitmap(R.drawable.player2pu, Bitmap.Config.ARGB_4444);
		loadBitmap(R.drawable.player2catch, Bitmap.Config.ARGB_4444);
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
		loadBitmap(R.drawable.model_bird_2_falling, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.building, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.facebook, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.facebook_green, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.twitter, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.background, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.highscores, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.button_social_big, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.pause, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.stop, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.openfeint, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.openfeint2, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.vibe, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.resume, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.replay, Bitmap.Config.ARGB_8888);

		loadBitmap(R.drawable.poison, Bitmap.Config.ARGB_8888);

		loadBitmap(R.drawable.model_bird_1_falling, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.airbird, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.ladybird, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.icebird, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.burnedbird, Bitmap.Config.ARGB_8888);
		loadBitmap(R.drawable.colorbird, Bitmap.Config.ARGB_8888);

		
		
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
