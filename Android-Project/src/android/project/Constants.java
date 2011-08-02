package android.project;

public class Constants {

	/* SCREEN ASPECT CONTROL CONSTANTS */
	
	public static final float ASPECT_WIDTH = 800;
	public static final float ASPECT_HEIGHT = 480;
	public static final float ASPECT_RATIO = ASPECT_WIDTH / ASPECT_HEIGHT;
	
	/* MATH CONSTANTS */
	
	public static final float MATH_EPSILON = (float) 1 / 1000;
	
	/* PATH CONTROL CONSTANTS */
	
	public static boolean PATH_CALCULATE_FRAME_RATE = true;
	
	/* 2D CONSTANTS */
	
	public static final float SCREEN_PLAYER_WIDTH = 200;
	public static final float SCREEN_PLAYER_HEIGHT = 57;
	public static final float SCREEN_PLAYER_MIDDLE_X = 450;
	public static final float SCREEN_PLAYER_MIDDLE_Y = 440;
	public static final float SCREEN_PLAYER_MOVE_TOTAL_X = 200;
	public static final float SCREEN_PLAYER_LEFT_X = SCREEN_PLAYER_MIDDLE_X - SCREEN_PLAYER_MOVE_TOTAL_X;
	public static final float SCREEN_PLAYER_RIGHT_X = SCREEN_PLAYER_MIDDLE_X + SCREEN_PLAYER_MOVE_TOTAL_X;
	public static final float SCREEN_PLAYER_SPEED_PPS = 600;
	
	/* ANIMATION CONSTANTS */
	
	public static final float ANIMATION_FRAME_RATE = 35;
	
	public static final long SMOKE_INTERVAL = 300;
	
}
