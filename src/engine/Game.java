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
	private int timeRemaining;
	private long timerTickTime;
	private boolean timerSet;
	private Camera camera;
	private boolean startMusicPlayed;

	public Game(Main main){
		this.main = main;
		initialize();
	}
	
	public void initialize(){
		levelNumber = 3;
		player = new Player();
		player.setFlashing(true);
		loadLevel(levelNumber);
		camera = new Camera(this);
		timeRemaining = 100;
	}

	public void loadLevel(int lvlNum){
		currentLevel = new Level(this);
		levelNumber = lvlNum;

		//used for getting the width and height of the sprite
		//this particular wall is not used for any other purpose
		Wall tempWall = new Wall(0, 0);
		try{
			BufferedImage mapImg = ImageIO.read( getClass().getResource("/resources/levels/level" + lvlNum + ".png"));

			for(int i = 0; i < mapImg.getWidth(); i++){
				for(int j = 0; j < mapImg.getHeight(); j++){
					Color c = new Color(mapImg.getRGB(i, j));					
					if(c.getBlue() == 0 && c.getRed() == 0 && c.getGreen() == 255){ //green - player
						player.setPosition(i * tempWall.getStepWidth(), j * tempWall.getStepHeight());
					}
					if(c.getBlue() == 0 && c.getRed() == 0 && c.getGreen() == 0){ //black - wall		
						currentLevel.addWall(new Wall(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 255 && c.getRed() == 0 && c.getGreen() == 0){ //blue - morph ball
						currentLevel.setMorphBall(new MorphBallPowerUp(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 255 && c.getRed() == 0 && c.getGreen() == 1){ //blue - missiles
						currentLevel.addMissilePowerUp(new MissilePowerUp(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 255 && c.getRed() == 0 && c.getGreen() == 2){ //blue - energy tank
						currentLevel.addEnergyTank(new EnergyTankPowerUp(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 255 && c.getRed() == 0 && c.getGreen() == 3){ //blue - elevator
						currentLevel.addElevator(new Elevator(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 255 && c.getRed() <= 1 && c.getGreen() == 4){ //blue - missile door, red indicates direction( 0 left, 1 right)
						currentLevel.addMissileDoor(new MissileDoor(i * tempWall.getStepWidth(), j * tempWall.getStepHeight(), c.getRed() == 0));
					}
					if(c.getBlue() == 128 && c.getRed() == 255 && c.getGreen() == 128){ //salmon - lava
						currentLevel.addLava(new Lava(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 0 && c.getRed() == 255 && c.getGreen() < 4){ // red - zoomer, green = up direction
						currentLevel.addEnemy(new Zoomer(i * tempWall.getStepWidth(), j * tempWall.getStepHeight(), c.getGreen()));
					}
					if(c.getBlue() == 1 && c.getRed() == 255 && c.getGreen() == 0){ // red - skree
						currentLevel.addEnemy(new Skree(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 2 && c.getRed() == 255 && c.getGreen() == 0){ // red - rinka spawner
						currentLevel.addRinkaSpawner(new RinkaSpawner(i * tempWall.getStepWidth(), j * tempWall.getStepHeight(), currentLevel));
					}
					if(c.getBlue() == 3 && c.getRed() == 255 && c.getGreen() == 0){ // red - mother brain
						currentLevel.addMotherBrain(new MotherBrain(i * tempWall.getStepWidth(), j * tempWall.getStepHeight(), this));
					}
					if(c.getBlue() == 4 && c.getRed() == 255 && c.getGreen() == 0){ // red - mother brain walls
						currentLevel.addMBWall(new Wall(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 5 && c.getRed() == 255 && c.getGreen() == 0){ // red - mother brain glass
						currentLevel.addMBGlass(new MBGlass(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 6 && c.getRed() == 255 && c.getGreen() == 0){ // red - mother brain glass
						currentLevel.addEnemy(new Spikes(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
					if(c.getBlue() == 128 && c.getRed() == 128 && c.getGreen() == 128){ // gray - music changer
						currentLevel.addMusicChanger(new MusicChanger(i * tempWall.getStepWidth(), j * tempWall.getStepHeight()));
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		for(Wall w : currentLevel.getWalls()){
//			w.loadGraphics();
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
			Sounds.playGameMusic1();
		}
		
		if(updateObjects){
			player.update(currentLevel);
			currentLevel.update();
		}
		
		if(player.getEnergy() < 1){
			Sounds.pauseGameMusic1();
			Sounds.pauseGameMusic2();
			Sounds.stopEscapeSequence();
			Sounds.playGameOver();
			Sounds.stopFootSteps();
			Sounds.stopLowEnergy();
			main.gameOver();
		}
		
		if(timerSet){
			if(timerTickTime + 1000 < System.currentTimeMillis()){
				timerTickTime = System.currentTimeMillis();
				timeRemaining--;
			}
		}
	}

	@Override
	public void draw(Graphics g){
		camera.draw(g);
	}
	
	public void collectItem(Sprite item){
		Sounds.pauseGameMusic();
		Sounds.playObtainPowerUp();
		try{
			Thread.sleep(3000);
		}catch(InterruptedException e){}
		
		if(item.getClass() == MorphBallPowerUp.class){
			player.setHasMorphBall(true);
		}
		else if(item.getClass() == MissilePowerUp.class){
			player.obtainMissileExpansion();
		}
		else if(item.getClass() == EnergyTankPowerUp.class){
			player.obtainEnergyTank();
		}
		Sounds.resumeGameMusic();
	}
	
	public Player getPlayer(){
		return player;
	}
	public Level getCurrentLevel(){
		return currentLevel;
	}
	public void startTimer(){
		timerSet = true;
	}
	public int getTimer(){
		return timeRemaining;
	}
	public boolean timerSet(){
		return timerSet;
	}
	public void winGame(){
		Sounds.playEndMusic();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		main.showCredits();
	}
}
