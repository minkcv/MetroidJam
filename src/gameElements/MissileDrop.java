package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MissileDrop extends Sprite{

	public MissileDrop(int x, int y) {
		super(x, y, 100, 1, 2);
		loadGraphics();
	}
	private void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/missiledrop.png"));
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
