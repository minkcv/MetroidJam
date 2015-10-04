package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MissileDoor extends Wall{
	public MissileDoor(int x, int y, boolean faceLeft) {
		super(x, y, 0, 1, 2);
		loadGraphics();
		if(!faceLeft)
			currentStep = 1;
	}
	
	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/missiledoor.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
