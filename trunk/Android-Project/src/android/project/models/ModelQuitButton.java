package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.bounds.BoundsCircle;

public class ModelQuitButton extends Object2D {
	
	public ModelQuitButton() {
		super(new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff8d2036);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(68);
		addObject(outer);
		p.setColor(0xffce0a31);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(69);
		addObject(inner);
		
		p.setColor(0xff8d2036);
		p.setStrokeWidth(7);
		Object2D line;
		int d = 28;
		addObject(line = new ModelRoundLine(d, d, -d / 2, -d / 2, p));
		line.setDepth(70);
		addObject(line = new ModelRoundLine(d, -d, -d/2, d/2, p));
		line.setDepth(70);
		setDepth(70);
	}
}