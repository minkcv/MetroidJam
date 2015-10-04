package gameElements;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Wall extends Sprite{
	
	public Wall(int x, int y){
		super(x, y, 0, 1, 1);
		stepWidth = 32;
		stepHeight = 32;
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
	}
	
	public Wall(int x, int y, int stepTime, int numStages, int numSteps){
		super(x, y, stepTime, numStages, numSteps);
	}
	
//	public void loadGraphics(){
//		try{
//			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/wall.png"));
//			super.loadGraphics(spriteImage);		
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}
}
