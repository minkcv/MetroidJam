package gameElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sprite {
	
	protected int xPosition;
	protected int yPosition;
	
	protected Rectangle bounding;
	
	protected BufferedImage sourceImage;
	protected BufferedImage[][] stepImages;
	protected int stepWidth;
	protected int stepHeight;
	protected int numSteps;
	protected int numStages;
	protected double stepTime; //use milliseconds
	protected double currentStepTime;
	protected int currentStep;
	protected int currentStage;	
	protected boolean toRemove;
	protected boolean onScreen;
	
	public Sprite(int x, int y, int stepTime, // time per frame 
								int numStages, // strips of animation
								int numSteps) // frames in cycle
	{
		xPosition = x;
		yPosition = y;
		this.stepTime = stepTime;
		this.numStages = numStages;
		this.numSteps = numSteps;
		currentStepTime = System.currentTimeMillis();
	}
	
	public void loadGraphics(BufferedImage sourceImage){
		this.sourceImage = sourceImage;
		stepHeight = sourceImage.getHeight() / numStages;
		stepWidth = sourceImage.getWidth() / numSteps;
		stepImages = new BufferedImage[numStages][numSteps];
		for(int i = 0; i < numStages; i++){
			for (int j = 0; j < numSteps; j++) {
				stepImages[i][j] = sourceImage.getSubimage(j * stepWidth, i * stepHeight, stepWidth, stepHeight);
			}
		}
		
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
	}
	
	public final void draw(Graphics g){
		g.drawImage(stepImages[currentStage][currentStep], xPosition, yPosition, null);		
	}
	
	public void drawAt(Graphics g, int x, int y){
		g.drawImage(stepImages[currentStage][currentStep], x, y, null);
//		g.setColor(Color.magenta);
//		g.drawRect(x, y, stepWidth, stepHeight);
	}
	
	public int getStepWidth() {
		return stepWidth;
	}

	public void setStepWidth(int stepWidth) {
		this.stepWidth = stepWidth;
	}

	public int getStepHeight() {
		return stepHeight;
	}

	public void setStepHeight(int stepHeight) {
		this.stepHeight = stepHeight;
	}
	
	public void setPosition(int x, int y){
		setxPosition(x);
		setyPosition(y);
	}
	
	public BufferedImage getStepImage(){
		return stepImages[currentStage][currentStep];
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public void setToRemove(boolean remove){
		toRemove = remove;
	}
	public boolean isToRemove(){
		return toRemove;
	}
	public boolean isOnScreen(){
		return onScreen;
	}
	public void setOnScreen(boolean onScr){
		onScreen = onScr;
	}
	public Rectangle getBounds(){
		return bounding;
	}
}