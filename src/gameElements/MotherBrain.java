package gameElements;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Game;
import engine.Sounds;

public class MotherBrain extends Enemy{
	private Game game;
	public MotherBrain(int x, int y, Game game) {
		super(x, y, 500, 2, 4, 50, 5);
		this.game = game;
		loadGraphics();
		stepWidth -= 32; // space for special walls
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
	}
	
	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/motherbrain.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(Player player, ArrayList<Wall> walls) {
		if(currentStepTime + stepTime < System.currentTimeMillis()){
			currentStep++;
			currentStep %= numSteps;
			currentStepTime = System.currentTimeMillis();
		}
		if(currentStage == 1 && currentStep == numSteps - 1){
			toRemove = true;
			game.getCurrentLevel().removeSpikes();
		}
	}
	
	@Override
	public void hit(int damage){
		health -= damage;
		if(health > 0){
			Sounds.playEnemyHit();
		}
		else{
			Sounds.playMotherBrainDie();
			currentStage = 1;
			currentStep = 0;
		}
	}
}
