package android.project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.media.AudioManager;
import android.media.SoundPool;
import android.project.R;
import android.util.Log;
import android.content.Context;

public class SoundManager {

	public static final int MENU_REGULAR_1 = R.raw.option4_1;
	public static final int MENU_REGULAR_2 = R.raw.option4_2;
	public static final int GAME_THEME = R.raw.gay_theme;
	public static final int JUMP = R.raw.jump;
	public static final int DEAD = R.raw.dead;
	public static final int NEW_BIRD1 = R.raw.new_bird_1;
	public static final int NEW_BIRD2 = R.raw.new_bird_2;
	public static final int NEW_BIRD3 = R.raw.new_bird_3;
	public static final int EASY1 = R.raw.easy1;
	public static final int EASY2 = R.raw.easy2;
	public static final int EASY3 = R.raw.easy3;
	public static final int MEDIUM1 = R.raw.medium1;
	public static final int MEDIUM2 = R.raw.medium2;
	public static final int HARD1 = R.raw.hard1;
	public static float volume = 1;
	public static final float speed = 0.1f;

	static HashMap<Integer,Sound> _sounds;
	static Context _context;
	static float _volume;
	static SoundPool _soundPool;
	static HashMap<Integer, Integer> _soundPoolMap;
	static Integer[] _songs={EASY1,EASY2,EASY3,MEDIUM1,MEDIUM2,HARD1};
	static Integer[] _alternatePoints={0,1,4,6,7,10};
	static int _curSongIndex;

	public static void pause(){
		Iterator<Sound> it = _sounds.values().iterator();
		Sound sound;
		while(it.hasNext()){
			sound = it.next();
			if(!sound.isCompleted())
				sound.getMp().pause();
		}
		_soundPool.autoPause();
	}
	public static void resume(){
		/*Iterator<Sound> it = _sounds.values().iterator();
		Sound sound;
		while(it.hasNext()){
			sound = it.next();
			if(!sound.isCompleted())
				sound.getMp().start();
		}
		//_soundPool.autoResume();
		 * */
		if(_curSongIndex >= 0){
			_sounds.get(_songs[_curSongIndex]).getMp().start();
			fadeIn();
		}
	}
	public static void stopSong(){
		if(_curSongIndex >= 0){
			_sounds.get(_songs[_curSongIndex]).getMp().pause();
		}

	}
	public static void playSong(int res){
		if(Utils.getSound() == false)
			return;
		Sound sound = _sounds.get(res);
		sound.getMp().seekTo(0);
		sound.getMp().start();
		sound.setNotCompleted();
		fadeIn();
	}
	public static void playFX(int res){
		_soundPool.play(_soundPoolMap.get(res), _volume, _volume, 1, 0, 1f); 
	}
	public static void initialize(){
		_curSongIndex = -1;
		_context = Utils.getActivity();

		_soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		_soundPoolMap = new HashMap<Integer, Integer>();
		_soundPoolMap.put(MENU_REGULAR_1, _soundPool.load(_context, MENU_REGULAR_1, 1));
		_soundPoolMap.put(MENU_REGULAR_2, _soundPool.load(_context, MENU_REGULAR_2, 1));
		_soundPoolMap.put(JUMP, _soundPool.load(_context, JUMP, 1));
		_soundPoolMap.put(DEAD, _soundPool.load(_context, DEAD, 1));
		_soundPoolMap.put(NEW_BIRD1, _soundPool.load(_context, NEW_BIRD1, 1));
		_soundPoolMap.put(NEW_BIRD2, _soundPool.load(_context, NEW_BIRD2, 1));
		_soundPoolMap.put(NEW_BIRD3, _soundPool.load(_context, NEW_BIRD3, 1));
		/*_soundPoolMap.put(EASY1, _soundPool.load(_context, EASY1, 1));
		_soundPoolMap.put(EASY2, _soundPool.load(_context, EASY2, 1));
		_soundPoolMap.put(EASY3, _soundPool.load(_context, EASY3, 1));
		_soundPoolMap.put(MEDIUM1, _soundPool.load(_context, MEDIUM1, 1));
		_soundPoolMap.put(MEDIUM2, _soundPool.load(_context, MEDIUM2, 1));
		_soundPoolMap.put(HARD1, _soundPool.load(_context, HARD1, 1));
		 */

		AudioManager mgr = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
		_volume = streamVolumeCurrent / streamVolumeMax;

		_sounds = new HashMap<Integer,Sound>();
		_sounds.put(GAME_THEME, new Sound(_context,GAME_THEME,true));
		_sounds.put(EASY1, new Sound(_context,EASY1,true));
		_sounds.put(EASY2, new Sound(_context,EASY2,true));
		_sounds.put(EASY3, new Sound(_context,EASY3,true));
		_sounds.put(MEDIUM1, new Sound(_context,MEDIUM1,true));
		_sounds.put(MEDIUM2, new Sound(_context,MEDIUM2,true));
		_sounds.put(HARD1, new Sound(_context,HARD1,true));
		SoundManager.stopSong();
	}
	public static void mute(boolean isOn) {
		if(!isOn){
			/*Iterator<Integer> it = _sounds.keySet().iterator();
			while(it.hasNext()){
				stopSong(it.next());
			}
			 */
			stopSong();
			Iterator<Integer> it = _soundPoolMap.keySet().iterator();
			while(it.hasNext()){
				_soundPool.stop(it.next());
			}
		}
	}
	public static void fadeOut(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(volume > 0){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					_sounds.get(_songs[_curSongIndex]).getMp().setVolume(volume, volume);
					volume -= speed;
				}				
			}
		}).start();
	}
	public static void fadeIn()
	{
		volume = 0;
		new Thread(new Runnable() {
			@Override
			public void run() {
				_sounds.get(_songs[_curSongIndex]).getMp().setVolume(volume, volume);
				while(volume < 1){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					_sounds.get(_songs[_curSongIndex]).getMp().setVolume(volume, volume);
					volume += speed;
				}
			}
		}).start();

	}

	public static void playNextSong(int level) {
		if(level == _alternatePoints[_curSongIndex + 1]){
			stopSong();
			++_curSongIndex;
			playSong(_songs[_curSongIndex]);
			fadeIn();
		}
	}
}
