package android.project.models;

import android.graphics.Paint;
import android.project.Object2D;
import android.project.Object2DBitmap;
import android.project.R;
import android.project.bounds.BoundsCircle;

public class ModelSoundOffButton extends Object2DBitmap {
	
	public ModelSoundOffButton() {
		super(R.drawable.sound_on2, new BoundsCircle(40), null, null, false, false, false, null);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(0xff248e8e);
		Object2D outer = new ModelCircle(40, 0, 0, p);
		outer.setDepth(48);
		addObject(outer);
		p.setColor(0xff00cccc);
		Object2D inner = new ModelCircle(33, 0, 0, p);
		inner.setDepth(49);
		addObject(inner);
		float l = 24;
		p.setColor(0xff248e8e);
		p.setStrokeWidth(7);
		Object2D line = new ModelRoundLine(2 * l, 2 * l, -l, -l, p);
		line.setDepth(50);
		addObject(line);
		setDepth(50);
	}
}