package android.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
	
	private enum Place {
		LEFT, MIDDLE, RIGHT, NONE
	}
	
	private Place[] _timeLine;
	private List<Integer> _indexList;
	private int _offset;
	private long _timePass;
	private float _v;

	public Scheduler() {
		_timeLine = new Place[Constants.TIME_CHUNKS];
		_offset = 0;
		_timePass = 0;
		_v = 0;
		
		for (int i = 0; i < Constants.TIME_CHUNKS; ++i)
			_timeLine[i] = Place.NONE;
		_indexList = new ArrayList<Integer>();
		for (int i = 0; i < 20; ++i)
			_indexList.add(i);
	}
	
	public void calculate(long timeDiff) {
		_v = 0;
		_timePass += timeDiff;
		if (_timePass < Constants.TIME_CHUNK)
			return;
		_timePass -= Constants.TIME_CHUNK;
		_timeLine[_offset] = Place.NONE;
		_offset = (_offset + 1) % Constants.TIME_CHUNKS;
		//Log.d("offset", "" + _offset);
		double r = Math.random();
		if (r > 0.1)
			return;
		Collections.shuffle(_indexList);
		for (int i = 0; i < 20; ++i) {
			_v = 50 + _indexList.get(i) * 5;
			if (propose(_v)) {
				accept(_v);
				return;
			}
		}
		_v = 0;
	}

	
	public float getNext() {
		return _v;
	}
	

	private boolean propose(float v) {
		if (v == 0)
			return false;
		float dx = Constants.SCREEN_PLAYER_MOVE_TOTAL_X / 2;
		float tLeft = (1000 * dx) / v;
		float tMiddle = 3 * tLeft;
		float tRight = 5 * tLeft;
		
		int tChunkLeft = (int) (tLeft / Constants.TIME_CHUNK);
		int tChunkMiddle = (int) (tMiddle / Constants.TIME_CHUNK);
		int tChunkRight = (int) (tRight / Constants.TIME_CHUNK);
		
		int chunksToCheck = (int) (Constants.TIME_REACT_TOTAL / Constants.TIME_CHUNK) + 1;
		
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
			
		/* Check middle */
		for (int i = -chunksToCheck; i < chunksToCheck + 1; ++i) {
			int index = (_offset + Constants.TIME_CHUNKS + Constants.TIME_CHUNK_OFFSET + i + tChunkMiddle) % Constants.TIME_CHUNKS;
			if (_timeLine[index] == Place.LEFT || _timeLine[index] == Place.RIGHT)
				return false;
		}
		
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
		return true;
	}
	
	private void accept(float v) {
		if (v == 0)
			return;
		float dx = Constants.SCREEN_PLAYER_MOVE_TOTAL_X / 2;
		float tLeft = (1000 * dx) / v;
		float tMiddle = 3 * tLeft;
		float tRight = 5 * tLeft;
		
		int tChunkLeft = (int) (tLeft / Constants.TIME_CHUNK);
		int tChunkMiddle = (int) (tMiddle / Constants.TIME_CHUNK);
		int tChunkRight = (int) (tRight / Constants.TIME_CHUNK);
		
		_timeLine[(_offset + Constants.TIME_CHUNK_OFFSET + tChunkLeft) % Constants.TIME_CHUNKS] = Place.LEFT;
		_timeLine[(_offset + Constants.TIME_CHUNK_OFFSET + tChunkMiddle) % Constants.TIME_CHUNKS] = Place.MIDDLE;
		_timeLine[(_offset + Constants.TIME_CHUNK_OFFSET + tChunkRight) % Constants.TIME_CHUNKS] = Place.RIGHT;
	}
	
}
