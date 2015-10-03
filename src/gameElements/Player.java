package gameElements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.Game;
import engine.Keyboard;
import engine.Sounds;

public class Player extends Sprite{

	private int xVelocity;
	private int yVelocity;
	private int knockbackX;
	private int knockbackY;
	private int knockbackSpeed;
	private double invulnerableTime = 2000;
	private double invulnerableStartTime;
	private boolean invulnerable;
	private int energy;
	private final int startEnergy = 30;
	private int energyCapacity;
	private int xMoveSpeed;
	private int jumpSpeed;
	private int fallFactor; // lower value falls faster
	private double fallFrames; // how long the player has been falling
	private int maxFallSpeed;
	private boolean touchingGround;
	private boolean zReleased;
	private int facingDirection;
	private boolean aimingUp;
	private boolean flashing;
	private boolean flashThisFrame;
	private Sprite gun;
	private boolean gunVisible = true;
	private boolean canShoot = true;
	private int maxBeams;
	private int maxMissiles; // on screen at one time
	private boolean xReleased;
	private boolean hasMissiles;
	private int missileCapacity;
	private int currentMissiles;
	private boolean missileMode;
	private Rectangle topRectangle;
	private Rectangle topLeftRectangle;
	private Rectangle topRightRectangle;
	private Rectangle rightRectangle;
	private Rectangle leftRectangle;
	private Rectangle bottomRectangle;
	private Rectangle bottomLeftRectangle;
	private Rectangle bottomRightRectangle;
	private Rectangle belowPlayersFeetRectangle;
	private Rectangle aboveMorphBallRectangle;
	
	private Rectangle walkBounds;
	private Rectangle spinJumpBounds;
	private Rectangle ballBounds;
	private Rectangle hitBox; // for enemy collisions
	
	private int spinJumpHeight;
	private int normalHeight;
	private boolean hasMorphBall;
	private boolean inMorphBall;
	private int morphBallHeight;
	private long lavaDamageStartTime;
	private long lavaDamageTime = 200;
	private boolean shiftReleased;
	private enum JumpState{
		ON_GROUND,
		SPIN_JUMP,
		NORMAL_JUMP
	}
	private JumpState jumpState;

	public Player(){
		super(0, 0, 100, 3, 4);
		gun = new Sprite(0, 0, 0, 2, 2);
		loadGraphics();
		
		energy = startEnergy;
		energyCapacity = 99;
		facingDirection = 0;
		zReleased = true;
		spinJumpHeight = stepHeight / 2;
		normalHeight = stepHeight;
		morphBallHeight = stepHeight / 2;
		
		// 2, 10, 145 seem to be close enough to the NES jump distance vertically and horizontally
		jumpSpeed = 2;
		maxFallSpeed = 10;
		fallFactor = 145;
		
		maxBeams = 3;
		maxMissiles = 3;
		
		xVelocity = 0;
		yVelocity = 0;
		xMoveSpeed = 4;
		knockbackSpeed = xMoveSpeed * 4;
		
		walkBounds = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
		spinJumpBounds = new Rectangle(xPosition, yPosition, stepWidth, stepHeight / 2);
		ballBounds = new Rectangle(xPosition, yPosition + stepHeight / 2, stepWidth, stepHeight / 2);
		hitBox = walkBounds;
	}

	public void loadGraphics(){
		try{
			BufferedImage spriteImage = ImageIO.read(getClass().getResource("/resources/player.png"));
			super.loadGraphics(spriteImage);
			BufferedImage gunImage = ImageIO.read(getClass().getResource("/resources/gun.png"));
			gun.loadGraphics(gunImage);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void update(Level currentLevel){
		knockbackX = 0;
		knockbackY = 0;
		
		if(Keyboard.SHIFT){
			if(currentMissiles > 0 && shiftReleased){
				missileMode = ! missileMode;
			}
			shiftReleased = false;
		}
		else{
			shiftReleased = true;
		}
		
		if(currentMissiles < 1){
			missileMode = false;
		}
		
		if(invulnerableStartTime + invulnerableTime < System.currentTimeMillis()){
			invulnerable = false;
			flashing = false;
		}
		
		if(invulnerable){
			flashing = true;
		}
		
		boolean inLava = false;
		for(Lava l : currentLevel.getLava()){
			if(this.bounding.intersects(l.bounding)){
				inLava = true;
				Sounds.playInLava();
			}
		}
		if(lavaDamageStartTime + lavaDamageTime < System.currentTimeMillis()){
			if(inLava){
				energy--;
				lavaDamageStartTime = System.currentTimeMillis();
			}
		}
		
		for(Enemy e : currentLevel.getEnemies()){
			if(this.bounding.intersects(e.bounding) && ! invulnerable){
				invulnerable = true;
				invulnerableStartTime = System.currentTimeMillis();
				Sounds.playPlayerHit();
				energy -= e.getDamage();
				if(this.xPosition < e.xPosition){
					knockbackX = -knockbackSpeed;
				}
				else if(this.xPosition > e.xPosition){
					knockbackX = knockbackSpeed;
				}
				if(this.yPosition < e.yPosition){
					knockbackY = -knockbackSpeed;
				}
				else if(this.yPosition > e.yPosition){
					knockbackY = knockbackSpeed;
				}
			}
		}
		
		collisionDetection(currentLevel);
		
		if(xVelocity != 0 && touchingGround && !inMorphBall){
			Sounds.playFootSteps();
		}
		else{
			Sounds.stopFootSteps();
		}
		
		animate();
		
		if(currentLevel.getBeams().size() < maxBeams && currentLevel.getMissiles().size() < maxMissiles && !inMorphBall){
			canShoot = true;
		}
		else{
			canShoot = false;
		}
		

		if(Keyboard.X){
			if(xReleased && canShoot){
				int dir = 0;
				if(facingDirection < 0)
					dir = 3;
				else
					dir = 1;
				if(aimingUp)
					dir = 0;
				if(!missileMode){
					currentLevel.addBeam(new Beam(gun.getxPosition(), gun.getyPosition(), dir));
				}
				else{
					currentLevel.addMissile(new Missile(gun.getxPosition(), gun.getyPosition(), dir));
					currentMissiles--;
				}
				if(jumpState == JumpState.SPIN_JUMP)
					jumpState = JumpState.NORMAL_JUMP;
				Sounds.playShoot();
			}
			xReleased = false;
		}
		else{
			xReleased = true;
		}
		
		if(energy > energyCapacity){
			energy = energyCapacity;
		}
		if(currentMissiles > missileCapacity){
			currentMissiles = missileCapacity;
		}
	}


	public void collisionDetection(Level currentLevel){
		ArrayList<Wall> walls = currentLevel.getWalls();
		
		walkBounds = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
		spinJumpBounds = new Rectangle(xPosition, yPosition, stepWidth, stepHeight / 2);
		ballBounds = new Rectangle(xPosition, yPosition + stepHeight / 2, stepWidth, stepHeight / 2);

		//horizontal move, gets overridden by knock back
		if(Keyboard.RIGHT){
			xVelocity = xMoveSpeed;
		}
		else if(Keyboard.LEFT){
			xVelocity = -xMoveSpeed;
		}
		else{
			xVelocity = 0;
		}

		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);

		//check if touching ground
		touchingGround = false;
		if(jumpState == JumpState.SPIN_JUMP)
			belowPlayersFeetRectangle = new Rectangle(xPosition, yPosition + stepHeight, stepWidth, 1);
		else
			belowPlayersFeetRectangle = new Rectangle(xPosition, yPosition + stepHeight, stepWidth, 1);
		
		for(Wall w : walls){
			if(belowPlayersFeetRectangle.intersects(w.bounding)){
				touchingGround = true;
				if(hasMorphBall && Keyboard.DOWN && !inMorphBall){ // enter morph ball
					Sounds.playEnterMorphBall();
					inMorphBall = true;
					stepHeight = morphBallHeight;
					yPosition += 5; // prevents you from sliding on to waist high platforms when you change
				}
				jumpState = JumpState.ON_GROUND;
				yVelocity = 0;
			}
		}

		//jumping
		if(Keyboard.Z){
			if(zReleased && touchingGround && !inMorphBall){
				touchingGround = false;
				Sounds.playJump();
				fallFrames = 0;
				if(xVelocity != 0){
					jumpState = JumpState.SPIN_JUMP;
				}
				else{
					jumpState = JumpState.NORMAL_JUMP;
				}
			}
			zReleased = false;
		}
		else{
			zReleased = true;
		}

		if(! zReleased && ! touchingGround){
			yVelocity -= jumpSpeed;		
		}

		if(!touchingGround){
			fallFrames += 15;
			yVelocity += (fallFrames / fallFactor);

			if(yVelocity > maxFallSpeed){
				yVelocity = maxFallSpeed;
			}
		}

		if(jumpState == JumpState.SPIN_JUMP){
//			stepHeight = spinJumpHeight;
			hitBox = spinJumpBounds;
		}
		else{
//			stepHeight = normalHeight;
			hitBox = walkBounds;
		}
		
		if(knockbackX != 0){
			xVelocity = knockbackX;
		}
		if(knockbackY != 0){
			yVelocity = knockbackY;
		}

		//update rectangles
		aboveMorphBallRectangle = new Rectangle(xPosition, yPosition - 1, stepWidth, 1);
		topRectangle = new Rectangle(xPosition, yPosition - Math.abs(yVelocity), stepWidth, Math.abs(yVelocity));
		topLeftRectangle = new Rectangle(xPosition - Math.abs(xVelocity), yPosition - Math.abs(yVelocity), stepWidth, Math.abs(yVelocity));
		topRightRectangle = new Rectangle(xPosition + stepWidth, yPosition - Math.abs(yVelocity), stepWidth, Math.abs(yVelocity));
		rightRectangle = new Rectangle(xPosition + stepWidth, yPosition, Math.abs(xVelocity), stepHeight + yVelocity);
		leftRectangle = new Rectangle(xPosition - Math.abs(xVelocity), yPosition, Math.abs(xVelocity), stepHeight + yVelocity);
		bottomLeftRectangle = new Rectangle(xPosition - Math.abs(xVelocity), yPosition + stepHeight, Math.abs(xVelocity), Math.abs(yVelocity));
		bottomRightRectangle = new Rectangle(xPosition + stepWidth, yPosition + stepHeight, Math.abs(xVelocity), Math.abs(yVelocity));
		bottomRectangle = new Rectangle(xPosition, yPosition + stepHeight, stepWidth, Math.abs(yVelocity));

		boolean rightCollides = false;
		boolean leftCollides = false;
		boolean openAboveMorphBall = true;

		int partialFallDistance = Integer.MAX_VALUE;
		int partialJumpDistance = Integer.MAX_VALUE;
		
		int partialXDistance = Integer.MAX_VALUE;

		Rectangle gapRect = new Rectangle(0, 0);
		boolean wallBelowGap = false;
		boolean wallAboveGap = false;
		for(Wall w : walls){

			//horizontal
			if(rightRectangle.intersects(w.bounding)){
				rightCollides = true;
			}
			if(leftRectangle.intersects(w.bounding)){
				leftCollides = true;
			}

			//vertical

			//top
			if(topRectangle.intersects(w.bounding)){
				int distanceToBottomOfWall = this.bounding.y - (w.bounding.y + w.getStepHeight());
				if(distanceToBottomOfWall < partialJumpDistance){
					partialJumpDistance = distanceToBottomOfWall;
				}
			}

			//bottom
			if(bottomRectangle.intersects(w.bounding)){
				int distanceToTopOfWall = w.bounding.y - (this.bounding.y + stepHeight);
				if(distanceToTopOfWall < partialFallDistance){
					partialFallDistance = distanceToTopOfWall;
				}
			}
			
			// morph ball
			if(aboveMorphBallRectangle.intersects(w.bounding)){
				openAboveMorphBall = false;
			}
			
			// 2 block tall gap between walls
			if((xVelocity < 0 && bottomLeftRectangle.intersects(w.bounding)) || (xVelocity > 0 && bottomRightRectangle.intersects(w.bounding))){
				gapRect = new Rectangle(w.getxPosition(), w.getyPosition() - 2 * w.getStepHeight(), w.getStepWidth(), 2 * w.getStepHeight());
				wallBelowGap = true;
			}
			if((xVelocity < 0 && topLeftRectangle.intersects(w.bounding)) || (xVelocity > 0 && topRightRectangle.intersects(w.bounding))){
				wallAboveGap = true;
			}
		}
		
		boolean gapOpen = true;
		for(Wall w : walls){
			if(gapRect.intersects(w.bounding)){
				gapOpen = false;
			}
		}
		
		if(gapOpen && wallBelowGap && wallAboveGap){
			partialFallDistance = (gapRect.y + gapRect.height) - (this.bounding.y + stepHeight);
		}

		//horizontal stopping
		if(leftCollides && xVelocity < 0 && !(gapOpen && wallBelowGap && wallAboveGap)){
			xVelocity = 0;
		}

		if(rightCollides && xVelocity > 0 && !(gapOpen && wallBelowGap && wallAboveGap)){
			xVelocity = 0;			
		}

		//vertical stopping
		if(touchingGround){
			if(yVelocity > 0){
				yVelocity = 0;
			}
		}
		else{ // bottom
			if(partialFallDistance != Integer.MAX_VALUE){
				if(yVelocity > 0){
					yVelocity = partialFallDistance;	
				}
			}
		}

		// top
		if(yVelocity < 0 && partialJumpDistance != Integer.MAX_VALUE){
			yVelocity = -partialJumpDistance;
		}
		
		// exit morph ball
		if(openAboveMorphBall && Keyboard.UP && inMorphBall){
			inMorphBall = false;
			stepHeight = normalHeight;
			yPosition -= morphBallHeight;
		}

		xPosition += xVelocity;
		yPosition += yVelocity;
	}// end collision detection

	public void animate(){
		currentStage = 0;
		//face left or right
		if(xVelocity < 0){
			facingDirection = -1;
		}
		else if(xVelocity > 0){
			facingDirection = 1;
		}
		
		if(Keyboard.UP){
			aimingUp = true;
		}
		else{
			aimingUp = false;
		}
		
		if(missileMode){
			gun.currentStage = 1;
		}
		else{
			gun.currentStage = 0;
		}
		
		if(aimingUp){
			gun.setyPosition(this.yPosition - gun.stepHeight);
			if(facingDirection < 0){
				gun.currentStep = 1;
				gun.setxPosition(this.xPosition);
			}
			else if(facingDirection > 0){
				gun.currentStep = 0;
				gun.setxPosition(this.xPosition);
			}
		}
		else{
			gun.setyPosition(this.yPosition);
			if(facingDirection < 0){
				gun.currentStep = 0;
				gun.setxPosition(this.xPosition - gun.stepWidth);
			}
			else if(facingDirection > 0){
				gun.currentStep = 1;
				gun.setxPosition(this.xPosition + this.stepWidth);
			}
		}
		
		if(inMorphBall || jumpState == JumpState.SPIN_JUMP)
			gunVisible = false;
		else
			gunVisible = true;

		//currentStage += facingDirection; // when player can face left or right

		//animate walking
		if(touchingGround || jumpState == JumpState.SPIN_JUMP){
			if(xVelocity != 0 || jumpState == JumpState.SPIN_JUMP){
				if(System.currentTimeMillis() > currentStepTime + stepTime){
					currentStepTime = System.currentTimeMillis();
					if(currentStep < numSteps - 1){
						currentStep++;
					}
					else{
						currentStep = 0;
					}
				}
			}
			else{
				currentStep = 0;
			}
		}
		else{ // jumping / falling
			currentStep = 1;
		}
		
		if(jumpState == JumpState.SPIN_JUMP){
			currentStage = 1;
		}
		if(inMorphBall){
			currentStage = 2;
		}
	}
	
	@Override
	public void drawAt(Graphics g, int x, int y){
		if(flashing){
			if(flashThisFrame){
				flashThisFrame = false;
			}
			else{
				g.drawImage(stepImages[currentStage][currentStep], x, y, null);
				flashThisFrame = true;
			}
		}
		else{
			g.drawImage(stepImages[currentStage][currentStep], x, y, null);
		}
	}

	public void setHasMorphBall(boolean hasMorphBall){
		this.hasMorphBall = hasMorphBall;
	}
	public void obtainMissileExpansion(){
		hasMissiles = true;
		missileCapacity += 5;
		currentMissiles += 5;
		if(currentMissiles > missileCapacity){
			currentMissiles = missileCapacity;
		}
	}
	public void obtainEnergyTank(){
		energyCapacity += 100;
		energy += 100;
		if(energy > energyCapacity){
			energy = energyCapacity;
		}
	}
	public int getMissileCount(){
		return currentMissiles;
	}
	public boolean hasMissiles(){
		return hasMissiles;
	}
	public void setFlashing(boolean flashing){
		this.flashing = flashing;
	}
	public Sprite getGun(){
		return gun;
	}
	public boolean isGunVisible(){
		return gunVisible;
	}
	public int getEnergy(){
		return energy;
	}
	public int getEnergyCapacity(){
		return energyCapacity;
	}
	public void gainEnergy(int energy){
		Sounds.playSmallPickup();
		this.energy += energy;
		if(this.energy > energyCapacity)
			this.energy = energyCapacity;
	}
	public void gainMissiles(int missiles){
		Sounds.playSmallPickup();
		currentMissiles += missiles;
		if(currentMissiles > missileCapacity)
			currentMissiles = missileCapacity;
	}
}
