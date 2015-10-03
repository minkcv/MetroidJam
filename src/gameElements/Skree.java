package gameElements;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Skree extends Enemy {
	private int detectionWidth = 5;
	private int detectionHeight = 10;
	private int xVelocity;
	private int yVelocity;
	private int moveSpeed;
	private Rectangle detectionRect;
	private long groundStartTime;
	private long groundTime = 1000;
	private boolean onGround;

	public Skree(int x, int y) {
		super(x, y, 100, 1, 2, 3, 8);
		loadGraphics();
		moveSpeed = 4;
	}
	
	private void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/skree.png"));
			super.loadGraphics(spriteImage);	
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void update(Player player, ArrayList<Wall> walls) {
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
		if(currentStepTime + stepTime < System.currentTimeMillis()){
			currentStep++;
			currentStep %= numSteps;
			currentStepTime = System.currentTimeMillis();
		}
		
		int width = walls.get(0).stepWidth;
		int height = walls.get(0).stepHeight;
		detectionRect = new Rectangle(xPosition - detectionWidth * width, yPosition, 2 * detectionWidth * width, detectionHeight * height);
		if(detectionRect.intersects(player.bounding)){
			yVelocity = moveSpeed;
			if(player.xPosition < this.xPosition)
				xVelocity = -moveSpeed;
			else if(player.xPosition > this.xPosition)
				xVelocity = moveSpeed;
		}
		
		for(Wall w : walls){
			if(this.bounding.intersects(w.bounding)){
				xVelocity = 0;
				yVelocity = 0;
				if(!onGround)
					groundStartTime = System.currentTimeMillis();
				onGround = true;
			}
		}
		
		if(onGround){
			if(groundStartTime + groundTime < System.currentTimeMillis()){
				toRemove = true;
			}
		}
		
		xPosition += xVelocity;
		yPosition += yVelocity;
	}
}
