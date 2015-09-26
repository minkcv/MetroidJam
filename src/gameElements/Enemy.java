package gameElements;

import java.util.ArrayList;

public abstract class Enemy extends Sprite{
	protected int health;
	public Enemy(int x, int y, int stepTime, int numStages, int numSteps, int health) {
		super(x, y, stepTime, numStages, numSteps);
		this.health = health;
	}
	
	public abstract void update(ArrayList<Wall> walls);
	public int getHealth(){
		return health;
	}
	public void hit(){
		health--;
	}
}
