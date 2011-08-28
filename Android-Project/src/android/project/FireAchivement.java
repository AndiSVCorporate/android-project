package android.project;
public abstract class FireAchivement {
	private final String _id;
	protected boolean _reached;
	
	public FireAchivement(String id){
		_id=id;
		_reached=false;
	}
	public boolean isReached(){
		return _reached;
	}
	protected void reach(){
		_reached=true;
		Utils.reachAchivement(_id);
	}
	public abstract boolean checkAchievement();
}
