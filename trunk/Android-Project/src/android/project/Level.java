package android.project;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import android.project.Scheduler.Place;
import android.project.models.BasicFallingObject;
import android.project.models.FallingObject;
import android.project.models.FireFallingObject;
import android.project.models.FreezeFallingObject;
import android.project.models.LifeFallingObject;
import android.project.models.OneJumpFallingObject;
import android.project.models.PoisonFallingObject;
import android.project.models.RandomOneJumpFallingObject;

public class Level {


	private Scheduler _sched;
	private Map<Bird,Integer> _toThrow;
	private Object2D _screen;
	private long _timeFromLast;
	private static float SPEED_RATIO=1;
	public enum Bird{
		ONE_JUMP, BASIC, FIRE, LIFE, RANDOM_ONE_JUMP, POISON, VAULT, FREEZE;
	}

	public Level(Map<Bird, Integer> birds, long react, Object2D screen, float basicV){
		_toThrow=birds;
		_sched=new Scheduler(react, basicV);
		_screen=screen;
		_timeFromLast=0;
	}
	public Level(Bird[] birdsKeys, int[] birdsNum, long react, Object2D screen, float basicV){
		_toThrow=new HashMap<Level.Bird, Integer>();
		for(int i=0;i<birdsKeys.length;i++)
			_toThrow.put(birdsKeys[i], birdsNum[i]);
		_sched=new Scheduler(react, basicV);
		_screen=screen;
		_timeFromLast=0;
	}
	
	public void calculate(long timeDiff){
		timeDiff*=SPEED_RATIO;
		_sched.calculate(timeDiff);
		_timeFromLast+=timeDiff;
	}
	
	public FallingObject getNext(){
		if(_timeFromLast<500)
			return null;
		Iterator<Integer> numOfBirds=_toThrow.values().iterator();
		int sum=0;
		while(numOfBirds.hasNext())
			sum+=numOfBirds.next();
		if(sum==0)
			return null;
		int r=new Random().nextInt(sum);
		Iterator<Bird> it=_toThrow.keySet().iterator();
		sum=0;
		Bird selected=Bird.BASIC;
		Bird cur;
		while(it.hasNext()){
			cur=it.next();
			sum+=_toThrow.get(cur);
			if(r<sum){
				selected=cur;
				break;
			}
		}
		Place p=Place.NONE;
		Place not=Place.NONE;
		if(selected==Bird.ONE_JUMP)
			p=Place.MIDDLE;

		if(selected==Bird.RANDOM_ONE_JUMP || selected==Bird.FIRE){
			int r2=new Random().nextInt(3);
			switch(r2){
			case 0: p=Place.LEFT; break;
			case 1: p=Place.MIDDLE; break;
			case 2: p=Place.RIGHT; break;
			}			
		}

		if(selected==Bird.POISON){
			int r2=new Random().nextInt(3);
			switch(r2){
			case 0: not=Place.LEFT; break;
			case 1: not=Place.MIDDLE; break;
			case 2: not=Place.RIGHT; break;
			}			
		}
		if(_sched.available(p,not)){
			float v = _sched.getNext();
			long tFall = (long) (100 * 1000 / v);
			float temp=new Random().nextFloat();
			if(Utils.floatCompare(temp, (float) 0.33)<0)
				temp=Constants.SCREEN_FLOOR_THIRD;
			else if(Utils.floatCompare(temp, (float) 0.66)<0)
				temp=(Constants.SCREEN_FLOOR_SECOND);
			else
				temp=(Constants.SCREEN_FLOOR_FIRST);
			_toThrow.put(selected, _toThrow.get(selected)-1);
			_timeFromLast=0;
			switch(selected){
			case ONE_JUMP: return new OneJumpFallingObject(tFall, temp, _screen);
			case BASIC: return new BasicFallingObject(tFall, temp, _screen);
			case FIRE: return new FireFallingObject(p,tFall, Constants.SCREEN_FLOOR_THIRD, _screen);
			case LIFE: return new LifeFallingObject(tFall, temp, _screen);
			case RANDOM_ONE_JUMP: return new RandomOneJumpFallingObject(p,tFall, temp, _screen);
			case POISON: return new PoisonFallingObject(not,tFall, temp, _screen);
			case FREEZE: return new FreezeFallingObject(tFall, temp, _screen);
			}
		}
		return null;
	}
	public boolean LevelDone(){
		Iterator<Integer> numOfBirds=_toThrow.values().iterator();
		int sum=0;
		while(numOfBirds.hasNext())
			sum+=numOfBirds.next();
		return sum==0;
	}
	public static void setSPEED_RATIO(float sPEED_RATIO) {
		SPEED_RATIO = sPEED_RATIO;
	}
	
}