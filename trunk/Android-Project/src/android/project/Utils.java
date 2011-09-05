package android.project;

import java.util.HashMap;
import java.util.Map;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.openfeint.api.OpenFeint;
import com.openfeint.api.OpenFeintDelegate;
import com.openfeint.api.OpenFeintSettings;
import com.openfeint.api.resource.Achievement;
import com.openfeint.api.resource.Leaderboard;
import com.openfeint.api.resource.Score;
import com.openfeint.api.ui.Dashboard;

public class Utils {


	private	static final String gameName = "Fire Birds";
	private	static final String gameID = "350222";
	private	static final String gameKey = "7WETVbnihN1kgwO9nWPCNw";
	private static final String gameSecret = "gjJK9Xtbo9UXxfzr2N0EEGWKHnSgvulOtUIWraFs";


	private static BitmapManager _bitmapManager = null;

	/* Global Matrixes for fast point calculation. */

	private static Matrix _canvasCalibrationMatrix = new Matrix();
	private static Matrix _inverseCanvasCalibrationMatrix = new Matrix();
	private static Matrix _canvasBaseMatrix = new Matrix();
	private static Matrix _inverseCanvasBaseMatrix = new Matrix();

	private static long _timeDiff = 0;

	private static Typeface _typeface;

	private static CanvasRenderer _canvasRenderer;

	private static Canvas _canvas;

	private static Activity _activity;

	private static boolean _soundOn = true;
	private static 	Facebook _facebook = new Facebook("124575674305461");
	private static boolean _backgroundOn = true;

	private static int _lastScore;
	
	private static Vibrator _vibrator=null;
	/* Global getters and setters to avoid passing endless arguments. */

	public static void setActivity(Activity activity) {
		_activity = activity;
	}

	public static void quit() {
		_activity.finish();
	}

	public static void setBitmapManager(BitmapManager bitmapManager) {
		_bitmapManager = bitmapManager;
	}

	public static BitmapManager getBitmapManager() {
		return _bitmapManager;
	}

	public static boolean getSound() {
		return _soundOn;
	}

	public static void setSound(boolean isOn) {
		_soundOn = isOn;
	}

	public static boolean getBackground() {
		return _backgroundOn;
	}

	public static void setBackground(boolean isOn) {
		_backgroundOn = isOn;
	}

	public static void setCanvasRenderer(CanvasRenderer canvasRenderer) {
		_canvasRenderer = canvasRenderer;
	}

	public static CanvasRenderer getCanvasRenderer() {
		return _canvasRenderer;
	}

	public static void setCanvas(Canvas canvas) {
		_canvas = canvas;
	}

	public static Canvas getCanvas() {
		return _canvas;
	}

	public static void setTypeface(Typeface typeface) {
		_typeface = typeface;
	}

	public static Typeface getTypeface() {
		return _typeface;
	}

	public static void setTimeDiff(long timeDiff) {
		_timeDiff = timeDiff;
	}

	public static long getTimeDiff() {
		return _timeDiff;
	}

	public static void setCanvasBaseMatrix(Matrix canvasBaseMatrix) {
		_canvasBaseMatrix = canvasBaseMatrix;
		_canvasBaseMatrix.invert(_inverseCanvasBaseMatrix);
	}

	public static Matrix getCanvasBaseMatrix() {
		return _canvasBaseMatrix;
	}

	public static Matrix getInverseCanvasBaseMatrix() {
		return _inverseCanvasBaseMatrix;
	}

	public static void setCanvasCalibrationMatrix(Matrix canvasCalibrationMatrix) {
		_canvasCalibrationMatrix = canvasCalibrationMatrix;
		_canvasCalibrationMatrix.invert(_inverseCanvasCalibrationMatrix);
	}

	public static Matrix getInverseCanvasCalibrationMatrix() {
		return _inverseCanvasCalibrationMatrix;
	}

	public static Matrix getCanvasCalibrationMatrix() {
		return _canvasCalibrationMatrix;
	}

	public static void postInvalidate(float left, float top, float right, float bottom) {

	}


	/* Float management helping functions. */

	/**
	 * Compares two floats.
	 * @param a first float.
	 * @param b second float.
	 * @return 0 if equal, 1 if a > b, -1 otherwise.
	 */
	public static int floatCompare(float a, float b) {
		if (a > b)
			if (a - Constants.MATH_EPSILON > b)
				return 1;
			else
				return 0;
		else
			if (b - Constants.MATH_EPSILON > a)
				return - 1;
			else
				return 0;
	}

	/**
	 * Rounds a float number.
	 * @param a the float number.
	 * @return the rounded number.
	 */
	public static int floatRound(float a) {
		int ra = (int) a;
		if (a - (float) ra >= 0.5)
			return ra + 1;
		return ra;
	}

	/* Canvas management helping functions. */

	/**
	 * Returns the destination of the given point in the canvas by
	 * calculating the transformation matrix.
	 * @param canvas the canvas.
	 * @param points the points to transform [x0 y0 x1 y1 ...].
	 */
	public static void mapPoints(Canvas canvas, float[] points) {
		Matrix transformationMatrix = getTransformationMatrix(canvas);
		transformationMatrix.mapPoints(points);
	}

	/**
	 * Returns the transformation matrix of the canvas.
	 * @param canvas the given canvas.
	 * @return transformation matrix of the canvas.
	 */
	public static Matrix getTransformationMatrix(Canvas canvas) {

		/* Denote B for canvas base matrix.
		 * Denote C for canvas calibration matrix.
		 * Denote T for canvas transformation matrix.
		 * Denote Cm for canvas matrix.
		 * 
		 * The following holds: Cm = B * C * T
		 * 
		 * So we return:
		 * 
		 * C ^ (-1) * B ^ (-1) * Cm =
		 * C ^ (-1) * B ^ (-1) * B * C * T = T 
		 */

		Matrix transformation = canvas.getMatrix();

		transformation.postConcat(_inverseCanvasBaseMatrix);
		transformation.postConcat(_inverseCanvasCalibrationMatrix);

		return transformation;
	}

	public static Matrix getBaseCalibrationMatrix() {

		Matrix baseCalibrationMatrix = new Matrix();


		baseCalibrationMatrix.preConcat(_canvasCalibrationMatrix);
		baseCalibrationMatrix.preConcat(_canvasBaseMatrix);

		return baseCalibrationMatrix;
	}

	public static void loginFacebook(){
		_facebook.authorize(_activity, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onFacebookError(FacebookError error) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onCancel() {
			}
		});
	}
	public static void postHighscore(int highScore){
		Bundle b=new Bundle();
		b.putString("picture", "https://fbcdn-photos-a.akamaihd.net/photos-ak-snc1/v43/141/124575674305461/app_1_124575674305461_1992.gif");
		b.putString("description", "I saved the birds from the fire and scored "+highScore+" points!");
		b.putString("name", "FireBirds!");
		b.putString("caption", "Save the birds from the fire. FAST!");
		_facebook.dialog(_activity, "feed", b,
				new DialogListener() {
			@Override
			public void onComplete(Bundle values) {}

			@Override
			public void onFacebookError(FacebookError error) {}

			@Override
			public void onError(DialogError e) {}

			@Override
			public void onCancel() {}
		}
				);
	}
	public static void navigateToFacebook(){
		String page="http://www.facebook.com/apps/application.php?id=124575674305461";
		Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(page));
		_activity.startActivity(viewIntent);
	}
	public static int saveHighscore(int score, int level){
		SharedPreferences pref=_activity.getPreferences(Context.MODE_PRIVATE);
		int curScore=pref.getInt("Highscore5", 0);
		int i;
		for(i=5;i>0;i--){
			curScore=pref.getInt("Highscore"+i, 0);
			if(curScore>score)
				break;
		}
		if(i==5){
			return -1;
		}
		Editor edit=pref.edit();
		edit.putInt("Highscore"+(i+1), score);
		edit.putInt("Level"+(i+1), level);
		edit.commit();
		return i+1;
	}
	public static android.project.Score[] getScores(){
		SharedPreferences pref=_activity.getPreferences(Context.MODE_PRIVATE);
		android.project.Score[] ret=new android.project.Score[5];
		for(int i=0;i<5;i++){
			ret[i]=new android.project.Score(i+1);		
		}
		return ret;
	}

	public static void postToOpenFeint(int score){
		if(!OpenFeint.isNetworkConnected())
			return;
		if(!OpenFeint.isUserLoggedIn())
			OpenFeint.login();
		String mLeaderboardID = "884267";//this.getIntent().getExtras().getString("leaderboard_id");
		final Score s = new Score(score);
		Leaderboard l = new Leaderboard(mLeaderboardID);
		s.submitTo(l, new Score.SubmitToCB() {
			@Override
			public void onSuccess(boolean newHighScore) {
				// TODO Auto-generated method stub				
			}	});
	}
	public static void reachAchivement(String id){
		if(!OpenFeint.isNetworkConnected())
			return;
		if(!OpenFeint.isUserLoggedIn())
			OpenFeint.login();
		final Achievement a = new Achievement(id);
		a.unlock(new Achievement.UnlockCB () {

			@Override
			public void onSuccess(boolean newUnlock) {
				// TODO Auto-generated method stub

			}});
	}
	public static void navigateToOpenfeint(){
		if(!OpenFeint.isNetworkConnected())
			return;
		if(!OpenFeint.isUserLoggedIn())
			OpenFeint.login();
		Dashboard.openLeaderboard("884267");
	}

	public static void initializeOpenfeint(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(OpenFeintSettings.SettingCloudStorageCompressionStrategy, OpenFeintSettings.CloudStorageCompressionStrategyDefault);
		// use the below line to set orientation
		options.put(OpenFeintSettings.RequestedOrientation, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		OpenFeintSettings settings = new OpenFeintSettings(gameName, gameKey, gameSecret, gameID);        
		SharedPreferences pref=_activity.getPreferences(Context.MODE_PRIVATE);
		if(pref.getBoolean("first", true)){
			Editor edit=pref.edit();
			edit.putBoolean("first", false);
			edit.commit();
			OpenFeint.initialize(_activity, settings, new OpenFeintDelegate() { });
		}
		OpenFeint.initializeWithoutLoggingIn(_activity, settings, new OpenFeintDelegate() { });
		
	}
	
	public static void vibrate(long duration){
		if(_vibrator!=null){
			_vibrator.vibrate(duration);
		}
	}

	public static void setVibration(boolean isOn) {
		_vibrator=isOn ? (Vibrator) _activity.getSystemService(Context.VIBRATOR_SERVICE):null;
	}

	public static boolean getVibration(){
		return _vibrator!=null;
	}
	
	public static Activity getActivity(){
		return _activity;
	}
	
	
	public static int get_lastScore() {
		return _lastScore;
	}

	public static void set_lastScore(int lastScore) {
		_lastScore = lastScore;
	}
}