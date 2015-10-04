package gameElements;

public class RinkaSpawner extends Wall{
	private Rinka rinka;
	private long rinkaWaitTime = 3000;
	private long rinkaWaitStartTime;
	private boolean wait;
	private Level level;
	public RinkaSpawner(int x, int y, Level level) {
		super(x, y);
		this.level = level;
	}
	
	public void update(){
		if(rinka == null){
			if(wait){
				rinkaWaitStartTime = System.currentTimeMillis();
				wait = false;
			}
			else if(rinkaWaitTime + rinkaWaitStartTime < System.currentTimeMillis()){
				rinka = new Rinka(xPosition, yPosition);
				level.addRinka(rinka);
				wait = true;
			}
		}
		else{
			if(!rinka.isOnScreen() || rinka.toRemove)
				destroyRinka();
		}
	}
	public Rinka getRinka(){
		return rinka;
	}
	public void destroyRinka(){
		level.removeRinka(rinka);
		rinka = null;
	}
}
