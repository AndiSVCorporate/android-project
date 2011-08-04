package android.project;

public class Scheduler {
	
	private boolean[] _timeLine;
	private int _offset;
	private long _timePass;

	public Scheduler() {
		_timeLine = new boolean[Constants.TIME_CHUNKS];
		_offset = 0;
		_timePass = 0;
		
		for (int i = 0; i < Constants.TIME_CHUNKS; ++i)
			_timeLine[i] = true;
	}
	
	public void calculate(long timeDiff) {
		_timePass += timeDiff;
		if (_timePass < Constants.TIME_MAX_DIFF)
			return;
		_timePass -= Constants.TIME_MAX_DIFF;
		_timeLine[_offset] = true;
		_offset = (_offset + 1) % Constants.TIME_CHUNKS;
	}
	
}
