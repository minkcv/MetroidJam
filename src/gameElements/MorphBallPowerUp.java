package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MorphBallPowerUp extends Sprite{

	public MorphBallPowerUp(int x, int y) {
		super(x, y, 80, 1, 3);
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/morphball.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}				
	}
	
	public void update(){
		if(currentStepTime + stepTime < System.currentTimeMillis()){
			currentStep++;
			currentStep %= numSteps;
			currentStepTime = System.currentTimeMillis();
		}
	}
}
