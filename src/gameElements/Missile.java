package gameElements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Missile extends Sprite{
	private int direction; // NESW 0123 (should never be S/2 though)
	private int moveSpeed;
	private int xVelocity;
	private int yVelocity;

	public Missile(int x, int y, int direction){
		super(x, y, 0, 4, 1);
		this.direction = direction;
		loadGraphics();
		moveSpeed = 6;
	}
	
	private void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/missile.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
		if(direction == 0){
			yVelocity = -moveSpeed;
			currentStage = 3;
		}
		else if(direction == 1){
			xVelocity = moveSpeed;
			currentStage = 2;
		}
		else if(direction == 2){
			yVelocity = moveSpeed;
			currentStage = 0;
		}
		else if(direction == 3){
			xVelocity = -moveSpeed;
			currentStage = 1;
		}
		
		xPosition += xVelocity;
		yPosition += yVelocity;
	}
	
	
	public void drawAt(Graphics g, int x, int y){
		g.drawImage(stepImages[currentStage][currentStep], x, y, null);
	}
}
