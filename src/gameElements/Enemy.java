package gameElements;

import java.util.ArrayList;

public abstract class Enemy extends Sprite{
	protected int health;
	protected int damage;
	public Enemy(int x, int y, int stepTime, int numStages, int numSteps, int health, int damage) {
		super(x, y, stepTime, numStages, numSteps);
		this.health = health;
		this.damage = damage;
	}
	
	public abstract void update(ArrayList<Wall> walls);
	public int getHealth(){
		return health;
	}
	public void hit(){
		health--;
	}
	public int getDamage(){
		return damage;
	}
}
