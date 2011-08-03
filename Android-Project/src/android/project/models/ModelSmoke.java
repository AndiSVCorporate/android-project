package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;
import android.project.Positioning;
import android.project.World;
import android.util.Log;

public class ModelSmoke extends Object2D {
	
	private long _totalTime;
	
	public ModelSmoke(float x, float y) {
		super(null, null,
				new Positioning(x, y, 1, 1, 0),
				false, false, false, null);
		_totalTime = 0;
	}
	
	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime < Constants.ANIMATION_SMOKE_INTERVAL)
			return;
		_totalTime = 0;
		ModelSmokeCircle smokeCircle = new ModelSmokeCircle(getX(), getY());
		getWorld().addObject(smokeCircle);
	}
	
	public static class ModelSmokeCircle extends Object2D {
		
		private float _radius;
		private float _alpha;
		private static Paint _paint = new Paint();
		
		static {
			_paint.setColor(Color.DKGRAY);
		}
		
		public ModelSmokeCircle(float x, float y) {
			super(null, null,
					new Positioning(x, y, 1, 1, 0),
					false, false, false, null);
			_alpha = 0xFF;
			_radius = 0;
		}

		@Override
		public void drawThis(Canvas c) {
			if (_alpha == 0)
				return;
			_paint.setAlpha((int) _alpha);
			c.drawCircle(0, 0, _radius, _paint);
		}
		
		@Override
		public void calculateThis(long timeDiff) {
			if (_alpha == 0)
				return;
			float newAlpha = _alpha - (0xFF * (((float) timeDiff) / Constants.ANIMATION_SMOKE_FADE_TIME));
			float newRadius = _radius + Constants.ANIMATION_SMOKE_MAX_RADIUS *  (((float) timeDiff) / Constants.ANIMATION_SMOKE_FADE_TIME);
			
			_radius = newRadius;
			_alpha = Math.max(0, newAlpha);
			if (_alpha == 0)
				getParent().removeObject(this);			
		}
		
		
		
	}

}
