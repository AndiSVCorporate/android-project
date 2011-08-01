package android.project;

import android.graphics.Canvas;

public class ModelSmoke extends Object2D {

	protected ModelSmoke(Screen container) {
		super(null, null, null, false, false, false);
	}

	private static final int SMOKE_INTERVAL = 300;
	
	@Override
	public void drawThis(Canvas c) {
		getX();
		getY();
		long t = Utils.getTime();
		long tp = Utils.getTimePrev();
		if (tp != 0 && t - tp > SMOKE_INTERVAL) {
		
		}
		
	}

}
