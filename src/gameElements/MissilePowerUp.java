package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MissilePowerUp extends Sprite{

	public MissilePowerUp(int x, int y) {
		super(x, y, 100, 1, 4);
		loadGraphics();
	}
	
	public void update(){
		if(currentStepTime + stepTime < System.currentTimeMillis()){
			currentStep++;
			currentStep %= numSteps;
			currentStepTime = System.currentTimeMillis();
		}
	}
	
	private void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/missilepowerup.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
