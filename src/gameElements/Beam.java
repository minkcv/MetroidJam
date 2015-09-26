package gameElements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Beam extends Sprite{
	private int direction; // NESW 0123 (should never be S/2 though)
	private int moveSpeed;
	private int xVelocity;
	private int yVelocity;

	public Beam(int x, int y, int direction){
		super(x, y, 0, 1, 1);
		this.direction = direction;
		loadGraphics();
		moveSpeed = 6;
	}
	
	private void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/beam.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		// a slightly smaller than image bounding box
		bounding = new Rectangle(xPosition + stepWidth / 4, yPosition + stepHeight / 4, stepWidth / 2, stepHeight / 2);
		if(direction == 0){
			yVelocity = -moveSpeed;
		}
		else if(direction == 1){
			xVelocity = moveSpeed;
		}
		else if(direction == 2){
			yVelocity = moveSpeed;
		}
		else if(direction == 3){
			xVelocity = -moveSpeed;
		}
		
		xPosition += xVelocity;
		yPosition += yVelocity;
	}
	
	
	public void drawAt(Graphics g, int x, int y){
		g.drawImage(stepImages[currentStage][currentStep], x, y, null);
	}
}
