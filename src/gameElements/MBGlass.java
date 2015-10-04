package gameElements;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MBGlass extends Enemy{

	public MBGlass(int x, int y) {
		super(x, y, 0, 1, 1, 10, 0);
		loadGraphics();
	}
	
	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/mbglass.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(Player player, ArrayList<Wall> walls) {
		
	}

}
