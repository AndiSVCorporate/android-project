package android.project.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.project.Constants;
import android.project.Object2D;
import android.project.Position;

public class ModelSmoke extends Object2D {
	
	private long _totalTime;
	private static final Paint[] RED={new Paint(Constants.PAINT_WHITE),
		new Paint(){{setColor(Color.RED);}},
		new Paint(){{setColor(Color.YELLOW);}},
		new Paint(){{setColor(0xffff6600);}}
	};

	private Paint[] _paints={new Paint(Constants.PAINT_WHITE),
			new Paint(Constants.PAINT_LTGRAY),
			new Paint(Constants.PAINT_DKGRAY),
			new Paint(Constants.PAINT_GRAY)
			};

	public ModelSmoke(float x, float y, int color) {
		super(null, null,
				new Position(x, y, 1, 1, 0),
				false, false, false, null);
		if(color==1)
			_paints=RED;
		_totalTime = 0;
	}
	
	@Override
	public void drawThis(Canvas c) { }
	
	@Override
	public void calculateThis(long timeDiff) {
		_totalTime += timeDiff;
		if (_totalTime < Constants.ANIMATION_SMOKE_INTERVAL)
			return;
		_totalTime -= Constants.ANIMATION_SMOKE_INTERVAL; 
		ModelSmokeCircle smokeCircle = new ModelSmokeCircle(getRealX(), getRealY(), _paints);
		getWorld().addObject(smokeCircle);
	}
	
	public static class ModelSmokeCircle extends Object2D {
		
		private float _radius;
		private float _alpha;
		private int _paintIndex;
		private float _skew;
		private float _maxRadius;
		
		private Paint[] _paints = {
			new Paint(Constants.PAINT_WHITE),
			new Paint(Constants.PAINT_LTGRAY),
			new Paint(Constants.PAINT_DKGRAY),
			new Paint(Constants.PAINT_GRAY)
			};
		
		public ModelSmokeCircle(float x, float y, Paint[] p) {
			super(null, null,
					new Position(x, y, 1, 1, 0),
					false, false, false, null);
			float skewStartX = (float) (Math.random() - 0.5) * 2 * Constants.ANIMATION_SMOKE_MAX_START_SKEW;
			float skewStartY = (float) (Math.random() - 0.5) * 2 * Constants.ANIMATION_SMOKE_MAX_START_SKEW;
			translate(skewStartX, skewStartY);
			
			_skew = (float) (Math.random() - 0.5) * 2 * Constants.ANIMATION_SMOKE_MAX_SKEW;
			_paintIndex = (int) Math.round(Math.random() * (_paints.length - 1));
			_alpha = 0xFF;
			_radius = 0;
			_maxRadius = Constants.ANIMATION_SMOKE_MIN_RADIUS + (float) Math.random() * Constants.ANIMATION_SMOKE_DIFF_RADIUS;
			_paints=p;
		}

		@Override
		public void drawThis(Canvas c) {
			if (_alpha == 0)
				return;
			_paints[_paintIndex].setAlpha((int) _alpha);
			c.drawCircle(0, 0, _radius, _paints[_paintIndex]);
		}
		
		@Override
		public void calculateThis(long timeDiff) {
			if (_alpha == 0)
				return;
			float newAlpha = _alpha - (0xFF * (((float) timeDiff) / Constants.ANIMATION_SMOKE_FADE_TIME));
			float newRadius = _radius + _maxRadius *  (((float) timeDiff) / Constants.ANIMATION_SMOKE_FADE_TIME);
			float dy = Constants.ANIMATION_SMOKE_DISTANCE * (((float) timeDiff) / Constants.ANIMATION_SMOKE_FADE_TIME);
			float dx = _skew * (((float) timeDiff) / Constants.ANIMATION_SMOKE_FADE_TIME);
			
			translate(dx, -dy);
			
			_radius = newRadius;
			_alpha = Math.max(0, newAlpha);
			if (_alpha == 0)
				getParent().removeObject(this);			
		}
		
	}

}
