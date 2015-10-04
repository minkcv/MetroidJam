package gameElements;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Rinka extends Enemy{

	private int xVelocity;
	private int yVeloctiy;
	private int moveSpeed;
	private boolean moveSet;
	public Rinka(int x, int y) {
		super(x, y, 0, 1, 1, 1, 5);
		loadGraphics();
		moveSpeed = 2;
	}

	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/rinka.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Player player, ArrayList<Wall> walls) {
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
		if(!moveSet){
			double dx = Math.abs(player.xPosition - xPosition);
			double dy = Math.abs(player.yPosition - yPosition);
			double hyp = Math.sqrt(dx * dx + dy * dy);
			xVelocity = (int) (dx / hyp * moveSpeed);
			yVeloctiy = (int) (dy / hyp * moveSpeed);
			if(player.xPosition < xPosition)
				xVelocity = -xVelocity;
			if(player.yPosition < yPosition)
				yVeloctiy = -yVeloctiy;
			moveSet = true;
		}
		xPosition += xVelocity;
		yPosition += yVeloctiy;
	}
}
