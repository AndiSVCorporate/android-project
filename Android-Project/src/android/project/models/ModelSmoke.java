package android.project.models;

import android.graphics.Canvas;
import android.project.Constants;
import android.project.Object2D;

public class ModelSmoke extends Object2D {
	
	private long _totalTime;
	
	public ModelSmoke(float x, float y) {
		super(null, null, null, false, false, false, null);
		_totalTime = 0;
	}
	
	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime < Constants.SMOKE_INTERVAL)
			return;
		_totalTime = 0;
		
	}
	
	public class ModelSmokeCircle extends Object2D {
		
		public ModelSmokeCircle(float x, float y) {
			super(null, null, null, false, false, false, null);
		}

		@Override
		public void drawThis(Canvas c) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}

}
