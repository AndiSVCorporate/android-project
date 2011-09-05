package android.project;

import android.graphics.Color;
import android.graphics.Paint;

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
	public static final float SCREEN_PLAYER_SPEED_PPS = 1000;
	public static final float SCREEN_TRAMPOLINE_HEIGHT = 450;	

	public static final float SCREEN_FLOOR_THIRD = 70;
	public static final float SCREEN_FLOOR_SECOND = 170;
	public static final float SCREEN_FLOOR_FIRST = 250;
	
	/* TIME CONSTANTS */
	
	public static final long TIME_MAX_DIFF = 50;
	public static final long TIME_CHUNK = 100;
	public static final int TIME_CHUNK_OFFSET = 10;
	public static final long TIME_FUTURE = 20000;
	public static final int TIME_CHUNKS = (int) (TIME_FUTURE / TIME_CHUNK);
	
	public static final long TIME_REACT_PLAYER = 500;
	public static final long TIME_SPEED_PLAYER = (long) ((1000 * SCREEN_PLAYER_MOVE_TOTAL_X) / SCREEN_PLAYER_SPEED_PPS);
	public static final long TIME_REACT_TOTAL = TIME_SPEED_PLAYER + TIME_REACT_PLAYER;
	
	/* ANIMATION CONSTANTS */
	
	public static final float ANIMATION_FRAME_RATE = 35;
	
	public static final long ANIMATION_SMOKE_INTERVAL = 100;
	public static final long ANIMATION_SMOKE_FADE_TIME = 500;
	public static final float ANIMATION_SMOKE_DISTANCE = 30;
	public static final float ANIMATION_SMOKE_MAX_SKEW = 10;
	public static final float ANIMATION_SMOKE_MAX_START_SKEW = 10;
	public static final float ANIMATION_SMOKE_MAX_RADIUS = 20;
	public static final float ANIMATION_SMOKE_MIN_RADIUS = 10;
	public static final float ANIMATION_SMOKE_DIFF_RADIUS = ANIMATION_SMOKE_MAX_RADIUS - ANIMATION_SMOKE_MIN_RADIUS;
	
	/* DEPTH CONSTANTS */
	
	public static final int DEPTH_PLAYER = 0;
	public static final int DEPTH_DEFUALT = 0;
	
	
	public static final int DEPTH_COVER = 1000000;
	public static final int DEPTH_BACKGROUND = Integer.MIN_VALUE;
	
	/* PAINTS */
	public static final Paint PAINT_RED = new Paint() {{ setColor(Color.RED); }};
	public static final Paint PAINT_GRAY = new Paint() {{ setColor(Color.GRAY); }};
	public static final Paint PAINT_LTGRAY = new Paint() {{ setColor(Color.LTGRAY); }};
	public static final Paint PAINT_DKGRAY = new Paint() {{ setColor(Color.DKGRAY); }};
	public static final Paint PAINT_WHITE = new Paint() {{ setColor(Color.WHITE); }};
	
	public static final int COLOR_GOLD = 0xffffd737;
	public static final int COLOR_BRONZE = 0xffe02e01;
	public static final int COLOR_SILVER = 0xffbbbbbb;
}
