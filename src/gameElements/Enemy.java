package gameElements;

import java.util.ArrayList;

import engine.Sounds;

public abstract class Enemy extends Sprite{
	protected int health;
	protected int damage;
	public Enemy(int x, int y, int stepTime, int numStages, int numSteps, int health, int damage) {
		super(x, y, stepTime, numStages, numSteps);
		this.health = health;
		this.damage = damage;
	}
	
	public abstract void update(Player player, ArrayList<Wall> walls);
	
	public int getHealth(){
		return health;
	}
	public void hit(int damage){
		health -= damage;
		if(health > 0){
			Sounds.playEnemyHit();
		}
		else{
			Sounds.playEnemyDie();
			toRemove = true;
		}
	}
	public int getDamage(){
		return damage;
	}
}
