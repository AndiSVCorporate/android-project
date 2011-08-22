package android.project.models;

import android.project.Object2D;

public class ModelGameBackground extends Object2D {

	public ModelGameBackground() {
		addObject(new ModelSkylineNear());
		addObject(new ModelSkylineFar());
	}
}
