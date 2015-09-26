package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Game;
import engine.Sounds;

public class Level {
	private Game game;
	private BufferedImage backGround;
	private ArrayList<Wall> walls;
	private ArrayList<Enemy> enemies;
	private ArrayList<Sprite> allElements;
	private ArrayList<Beam> beams;
	private MorphBallPowerUp morphBall;
	
	public Level(Game game){
		this.game = game;
		allElements = new ArrayList<Sprite>();
		enemies = new ArrayList<Enemy>();
		walls = new ArrayList<Wall>();
		beams = new ArrayList<Beam>();
	}
	
	public void loadBackGround(int levelNumber){
		try{
			backGround = ImageIO.read(getClass().getResource("/resources/backgrounds/back"  + levelNumber + ".png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		if(morphBall != null){
			morphBall.update();
			if(game.getPlayer().bounding.intersects(morphBall.bounding)){
				Sounds.playObtainPowerUp();
				game.collectItem(morphBall);
				allElements.remove(morphBall);
				morphBall = null;
			}
		}
		
		for(Beam b : beams){
			b.update();
			for(Enemy e : enemies){
				if(b.bounding.intersects(e.bounding)){
					e.hit();
					Sounds.playEnemyHit();
					b.setToRemove(true);
				}
			}
			for(Wall w : walls){
				if(b.bounding.intersects(w.bounding)){
					b.setToRemove(true);
				}
			}
		}
		for(Enemy e : enemies){
			e.update(walls);
		}
		
		for(Beam b : beams){
			if(b.isToRemove()){
				beams.remove(b);
				break;
			}
		}
		for(Enemy e : enemies){
			if(e.isToRemove()){
				enemies.remove(e);
				break;
			}
		}
	}
	
	public BufferedImage getBackGround(){
		return backGround;
	}
	
	public void addWall(Wall w){
		walls.add(w);
		allElements.add(w);
	}
	
	public void addEnemy(Enemy e){
		enemies.add(e);
		allElements.add(e);
	}
	
	public void setMorphBall(MorphBallPowerUp m){
		morphBall = m;
		allElements.add(m);
	}
	
	public MorphBallPowerUp getMorphBall(){
		return morphBall;
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	public void addBeam(Beam b){
		beams.add(b);
		allElements.add(b);
	}
	public ArrayList<Beam> getBeams(){
		return beams;
	}
	public ArrayList<Sprite> getAllElements(){
		return allElements;
	}
}