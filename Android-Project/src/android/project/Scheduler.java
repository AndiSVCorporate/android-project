package android.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

public class Scheduler {

	public enum Place {
		LEFT, MIDDLE, RIGHT, NONE
	}

	private Place[] _timeLine;
	private List<Integer> _indexList;
	private int _offset;
	private long _timePass;
	private float _v;
	private float _basicV;
	private long _react;
	public Scheduler(long react, float v) {
		_timeLine = new Place[Constants.TIME_CHUNKS];
		_offset = 0;
		_timePass = 0;
		_v = 0;
		_basicV=v;
		_react=react;
		for (int i = 0; i < Constants.TIME_CHUNKS; ++i)
			_timeLine[i] = Place.NONE;
		_indexList = new ArrayList<Integer>();
		for (int i = 0; i < 10; ++i)
			_indexList.add(i);
	}

	public void calculate(long timeDiff) {
		_v=0;
		_timePass += timeDiff;
		if (_timePass < Constants.TIME_CHUNK)
			return;
		_timePass -= Constants.TIME_CHUNK;
		_timeLine[_offset] = Place.NONE;
		_offset = (_offset + 1) % Constants.TIME_CHUNKS;
	}

	public boolean available(Place p, Place not){
		_v=0;
		double r = Math.random();
		if (r > 0.05)
			return false;
		Collections.shuffle(_indexList);
		for (int i = 0; i < _indexList.size(); ++i) {
			_v = _basicV + _indexList.get(i) * 5;
			if (propose(p, not)) {
				accept(p, not);
				return true;
			}
		}
		_v = 0;
		return false;
	}

	public float getNext() {
		return _v;
	}


	private boolean propose(Place only, Place not) {
		if (_v == 0)
			return false;
		float dx = Constants.SCREEN_PLAYER_MOVE_TOTAL_X / 2;
		float tLeft = (1000 * dx) / _v;
		float tMiddle = 3 * tLeft;
		float tRight = 5 * tLeft;
		Log.d("POSITION", "ONLY:"+only+" NOT:"+not);
		int tChunkLeft = (int) (tLeft / Constants.TIME_CHUNK);
		int tChunkMiddle = (int) (tMiddle / Constants.TIME_CHUNK);
		int tChunkRight = (int) (tRight / Constants.TIME_CHUNK);
		if(only!=Place.NONE || not!=Place.NONE){
			Log.d("POSITION", "in if 1");
			tChunkMiddle=tChunkLeft;
			tChunkRight=tChunkLeft;
		}

		int chunksToCheck = (int) (_react / Constants.TIME_CHUNK) + 1;

		if((only==Place.LEFT || only==Place.NONE) && not!=Place.LEFT){
			/* Check left */
			for (int i = -2 * chunksToCheck; i < 2 * chunksToCheck + 1; ++i) {
				int index = (_offset + Constants.TIME_CHUNKS + Constants.TIME_CHUNK_OFFSET + i + tChunkLeft) % Constants.TIME_CHUNKS;
				if (_timeLine[index] == Place.RIGHT)
					return false;
			}
			for (int i = -chunksToCheck; i < chunksToCheck + 1; ++i) {
				int index = (_offset + Constants.TIME_CHUNKS + Constants.TIME_CHUNK_OFFSET + i + tChunkLeft) % Constants.TIME_CHUNKS;
				if (_timeLine[index] == Place.MIDDLE)
					return false;
			}
		}
		if((only==Place.MIDDLE || only==Place.NONE) && not!=Place.MIDDLE){
			Log.d("POSITION", "in if 2");
			/* Check middle */
			for (int i = -chunksToCheck; i < chunksToCheck + 1; ++i) {
				int index = (_offset + Constants.TIME_CHUNKS + Constants.TIME_CHUNK_OFFSET + i + tChunkMiddle) % Constants.TIME_CHUNKS;
				if (_timeLine[index] == Place.LEFT || _timeLine[index] == Place.RIGHT)
					return false;
			}
		}
		if((only==Place.RIGHT || only==Place.NONE) && not!=Place.RIGHT){
			/* Check right */
			for (int i = -2 * chunksToCheck; i < 2 * chunksToCheck + 1; ++i) {
				int index = (_offset + Constants.TIME_CHUNKS + Constants.TIME_CHUNK_OFFSET + i + tChunkRight) % Constants.TIME_CHUNKS;
				if (_timeLine[index] == Place.LEFT)
					return false;
			}
			for (int i = -chunksToCheck; i < chunksToCheck + 1; ++i) {
				int index = (_offset + Constants.TIME_CHUNKS + Constants.TIME_CHUNK_OFFSET + i + tChunkRight) % Constants.TIME_CHUNKS;
				if (_timeLine[index] == Place.MIDDLE)
					return false;
			}
		}
		return true;
	}

	private void accept(Place only, Place not) {
		if (_v == 0)
			return;
		float dx = Constants.SCREEN_PLAYER_MOVE_TOTAL_X / 2;
		float tLeft = (1000 * dx) / _v;
		float tMiddle = 3 * tLeft;
		float tRight = 5 * tLeft;

		int tChunkLeft = (int) (tLeft / Constants.TIME_CHUNK);
		int tChunkMiddle = (int) (tMiddle / Constants.TIME_CHUNK);
		int tChunkRight = (int) (tRight / Constants.TIME_CHUNK);

		if(only!=Place.NONE || not!=Place.NONE){
			Log.d("POSITION", "in if 3");
			tChunkMiddle=tChunkLeft;
			tChunkRight=tChunkLeft;
		}

		if((only==Place.NONE || only==Place.LEFT) && not!=Place.LEFT)
			_timeLine[(_offset + Constants.TIME_CHUNK_OFFSET + tChunkLeft) % Constants.TIME_CHUNKS] = Place.LEFT;
		if((only==Place.NONE || only==Place.MIDDLE) && not!=Place.MIDDLE){
			_timeLine[(_offset + Constants.TIME_CHUNK_OFFSET + tChunkMiddle) % Constants.TIME_CHUNKS] = Place.MIDDLE;
			Log.d("POSITION", "in if 4");
		}
		if((only==Place.NONE || only==Place.RIGHT) && not!=Place.RIGHT)
			_timeLine[(_offset + Constants.TIME_CHUNK_OFFSET + tChunkRight) % Constants.TIME_CHUNKS] = Place.RIGHT;
	}

}