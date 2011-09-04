package android.project;

import android.content.Context;
import android.content.SharedPreferences;

public class Score {
	private int _score;
	private int _level;
	public Score(int i){
		SharedPreferences pref=Utils.getActivity().getPreferences(Context.MODE_PRIVATE);
		_score=pref.getInt("Highscore"+i, 0);
		_level=pref.getInt("Level"+i, 0);
	}
	public int get_score() {
		return _score;
	}
	public int get_level() {
		return _level;
	}
}
