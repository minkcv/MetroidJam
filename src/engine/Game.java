package engine;

import gameElements.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Game extends Interactive{
	private Main main;
	private Player player;
	private boolean updateObjects;
	private Level currentLevel;
	private int levelNumber;

	private Camera camera;
	private boolean startMusicPlayed;

	public Game(Main main){
		this.main = main;
		initialize();
	}
	
	public void initialize(){
		levelNumber = 1;
		player = new Player();
		player.setFlashing(true);
		loadLevel(levelNumber);
		camera = new Camera(this);
	}



	public void loadLevel(int lvlNum){
		currentLevel = new Level(this);
		levelNumber = lvlNum;

		//used for getting the width and height of the sprite
		//this particular wall is not used for any other purpose
		Wall tempWall = new Wall(0, 0);
		tempWall.loadGraphics();
		try{
			BufferedImage mapImg = ImageIO.read( getClass().getResource("/resources/levels/level" + lvlNum + ".png"));

			for(int i = 0; i < mapImg.getWidth(); i++){
				for(int j = 0; j < mapImg.getHeight(); j++){
					Color c = new Color(mapImg.getRGB(i, j));					
					if(c.getBlue() == 0 && c.getRed() == 0 && c.getGreen() == 255){ //green
						player.setPosition(i * tempWall.getStepWidth(), j * tempWall.getStepHeight());
					}
					if(c.getBlue() == 0 && c.getRed() == 0 && c.getGreen() == 0){ //black				
						currentLevel.addWall(new Wall(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 255 && c.getRed() == 0 && c.getGreen() == 0){
						currentLevel.setMorphBall(new MorphBallPowerUp(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 0 && c.getRed() == 255 && c.getGreen() < 4){
						currentLevel.addEnemy(new Zoomer(i * tempWall.getStepWidth(), j * tempWall.getStepHeight(), c.getGreen()));
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		for(Wall w : currentLevel.getWalls()){
			w.loadGraphics();
		}
		currentLevel.loadBackGround(levelNumber);
		tempWall = null;
	}

	@Override
	public void update(){
		if(Keyboard.ESCAPE){
			System.exit(0);
		}
		
		if(! startMusicPlayed){
			Sounds.playStart();
			startMusicPlayed = true;
		}
		
		if(Sounds.startFinished()){
			updateObjects = true;
			player.setFlashing(false);
		}
		
		if(updateObjects){
			player.update(currentLevel);
			currentLevel.update();
		}
		
		if(player.getEnergy() < 1){
			main.gameOver();
		}
	}

	@Override
	public void draw(Graphics g){
		camera.draw(g);
	}
	
	public void collectItem(Sprite item){
		if(item.getClass() == MorphBallPowerUp.class){
			try{
				Thread.sleep(3000);
			}catch(InterruptedException e){}
			player.setHasMorphBall(true);
		}
	}
	
	public Player getPlayer(){
		return player;
	}
	public Level getCurrentLevel(){
		return currentLevel;
	}
}
