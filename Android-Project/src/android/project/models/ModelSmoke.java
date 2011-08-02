package android.project.models;

import android.graphics.Canvas;
import android.project.Object2D;
import android.project.Utils;

public class ModelSmoke extends Object2D {

	protected ModelSmoke() {
		super(null, null, null, false, false, false, null);
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
