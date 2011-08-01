package android.project;

public class ModelGameSplash extends Object2DBitmap {
	
	public ModelGameSplash() {
		super(R.drawable.game_screen,
				null,
				null,
				new Positioning(Constants.ASPECT_WIDTH / 2, Constants.ASPECT_HEIGHT / 2, 1, 1, 0),
				false, false, false);
	}
}