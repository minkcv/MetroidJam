package gameElements;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Sounds;

public class Zoomer extends Enemy{

	private int xVelocity;
	private int yVeloctiy;
	private int moveSpeed;
	private int upDirection; // NESW 0123
	private Rectangle topRectangle;
	private Rectangle topLeftRectangle;
	private Rectangle topRightRectangle;
	private Rectangle leftRectangle;
	private Rectangle rightRectangle;
	private Rectangle bottomRectangle;
	private Rectangle bottomLeftRectangle;
	private Rectangle bottomRightRectangle;
	
	public Zoomer(int x, int y, int dir) {
		super(x, y, 100, 4, 2, 2);
		upDirection = dir;
		moveSpeed = 1;
		loadGraphics();
	}
	
	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/zoomer.png"));
			super.loadGraphics(spriteImage);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void update(ArrayList<Wall> walls){
		if(health < 1){
			toRemove = true;
		}
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
		boolean topCollides = false;
		boolean topLeftCollides = false;
		boolean topRightCollides = false;
		boolean leftCollides = false;
		boolean rightCollides = false;
		boolean bottomCollides = false;
		boolean bottomLeftCollides = false;
		boolean bottomRightCollides = false;
		topRectangle = new Rectangle(xPosition, yPosition - moveSpeed, stepWidth, moveSpeed);
		topLeftRectangle = new Rectangle(xPosition - moveSpeed, yPosition - moveSpeed, moveSpeed, moveSpeed);
		topRightRectangle = new Rectangle(xPosition + stepWidth, yPosition - moveSpeed, moveSpeed, moveSpeed);
		leftRectangle = new Rectangle(xPosition - moveSpeed, yPosition, moveSpeed, stepHeight);
		rightRectangle = new Rectangle(xPosition + stepWidth, yPosition, moveSpeed, stepHeight);
		bottomRectangle = new Rectangle(xPosition, yPosition + stepHeight, stepWidth, moveSpeed);
		bottomLeftRectangle = new Rectangle(xPosition - moveSpeed, yPosition + stepHeight, moveSpeed, moveSpeed);
		bottomRightRectangle = new Rectangle(xPosition + stepWidth, yPosition + stepHeight, moveSpeed, moveSpeed);
		for(Wall w : walls){
			if(topRectangle.intersects(w.bounding)){
				topCollides = true;
			}
			if(topLeftRectangle.intersects(w.bounding)){
				topLeftCollides = true;
			}
			if(topRightRectangle.intersects(w.bounding)){
				topRightCollides = true;
			}
			if(leftRectangle.intersects(w.bounding)){
				leftCollides = true;
			}
			if(rightRectangle.intersects(w.bounding)){
				rightCollides = true;
			}
			if(bottomRectangle.intersects(w.bounding)){
				bottomCollides = true;
			}
			if(bottomLeftRectangle.intersects(w.bounding)){
				bottomLeftCollides = true;
			}
			if(bottomRightRectangle.intersects(w.bounding)){
				bottomRightCollides = true;
			}
		}
		
		
//		System.out.println("dir: " + upDirection + " lastDir: " + lastUpDirection);
		
		if(upDirection == 0){
			if(!bottomCollides){
				upDirection = 1;
			}
			else if(rightCollides){
				upDirection = 3;
			}
		}
		else if(upDirection == 1){
			if(!leftCollides){
				upDirection = 2;
			}
			else if(bottomCollides){
				upDirection = 0;
			}
		}
		else if(upDirection == 2){
			if(!topCollides){
				upDirection = 3;
			}
			else if(leftCollides){
				upDirection = 1;
			}
		}
		else if(upDirection == 3){
			if(!rightCollides){
				upDirection = 0;
			}
			else if(topCollides){
				upDirection = 2;
			}
		}
		
		if(upDirection == 0){
			xVelocity = moveSpeed;
			yVeloctiy = 0;
		}
		else if(upDirection == 1){
			yVeloctiy = moveSpeed;
			xVelocity = 0;
		}
		else if(upDirection == 2){
			xVelocity = -moveSpeed;
			yVeloctiy = 0;
		}
		else if(upDirection == 3){
			yVeloctiy = -moveSpeed;
			xVelocity = 0;
		}
		
		xPosition += xVelocity;
		yPosition += yVeloctiy;
	}
}
