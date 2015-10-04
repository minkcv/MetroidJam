package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Elevator extends Sprite{

	public Elevator(int x, int y) {
		super(x, y, 0, 1, 1);
		loadGraphics();
	}
	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/elevator.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
