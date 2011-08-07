package android.project.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.project.Object2D;
import android.project.Positioning;

public class ModelPlayButton extends Object2D {
	
	public ModelPlayButton(int x, int y) {
		super(null,
				null,
				new Positioning(x, y, 1, 1, 0),
				false, false, false, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xff92cc47);
		addObject(new ModelCircle(120, 100, 0, 0, paint));
		paint.setColor(0xff9aef3f);
		addObject(new ModelCircle(121, 93, 0, 0, paint));
		paint.setStrokeWidth(7);
		paint.setColor(0xff92cc47);
		addObject(new ModelRoundLine(121, 58, 0, -71, -20, paint));
		addObject(new ModelRoundLine(121, 58, 0, -71, 20, paint));
		
		addObject(new ModelRoundLine(121, 0, -40, -71, 20, paint));
		
		addObject(new ModelRoundLine(121, 0, -30, -71 + 58, -20, paint));
		addObject(new ModelRoundLine(121, 0, 30, -71 + 58, 20, paint));
		
		addObject(new ModelRoundLine(121, 90, 50, -71 + 58, -50, paint));
		addObject(new ModelRoundLine(121, 90, -50, -71 + 58, 50, paint));
		
	}
	
	@Override
	public int depth() {
		return 120;
	}

	@Override
	public void drawThis(Canvas c) { }
}
