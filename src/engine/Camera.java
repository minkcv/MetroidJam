package engine;

import gameElements.Beam;
import gameElements.Enemy;
import gameElements.Level;
import gameElements.MorphBallPowerUp;
import gameElements.Player;
import gameElements.Wall;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Camera {
	private Game game;
	private Player player;
	private Level level;
	private Rectangle cameraBounds;
	private MorphBallPowerUp morphBall;
	public Camera(Game game){
		this.game = game;
		player = game.getPlayer();
		level = game.getCurrentLevel();
	}
	public void draw(Graphics g){
		morphBall = level.getMorphBall();
		BufferedImage backGround = level.getBackGround();
		int xShift = 0;
		int yShift = 0;
		// top left
		if(player.getxPosition() < Main.WIDTH / 2 && player.getyPosition() < Main.HEIGHT / 2){
//			System.out.println("top left");
		}
		// bottom left
		else if(player.getxPosition() < Main.WIDTH / 2 && player.getyPosition() > backGround.getHeight() - Main.HEIGHT / 2){
//			System.out.println("bottom left");
			yShift = backGround.getHeight() - Main.HEIGHT;
		}
		// top right
		else if(player.getxPosition() > backGround.getWidth() - Main.WIDTH / 2 && player.getyPosition() < Main.HEIGHT / 2){
//			System.out.println("top right");
			xShift = backGround.getWidth() - Main.WIDTH;
		}
		// bottom right
		else if(player.getxPosition() > backGround.getWidth() - Main.WIDTH / 2 && player.getyPosition() > backGround.getHeight() - Main.HEIGHT / 2){
//			System.out.println("bottom right");
			xShift = backGround.getWidth() - Main.WIDTH;
			yShift = backGround.getHeight() - Main.HEIGHT;
		}
		// left
		else if(player.getxPosition() < Main.WIDTH / 2){
//			System.out.println("left");
			yShift = player.getyPosition() - Main.HEIGHT / 2;
		}
		// top
		else if(player.getyPosition() < Main.HEIGHT / 2){
//			System.out.println("top");
			xShift = player.getxPosition() - Main.WIDTH / 2;
		}
		// right
		else if(player.getxPosition() > backGround.getWidth() - Main.WIDTH / 2){
//			System.out.println("right");
			xShift = backGround.getWidth() - Main.WIDTH;
			yShift = player.getyPosition() - Main.HEIGHT / 2;
		}
		// bottom
		else if(player.getyPosition() > backGround.getHeight() - Main.HEIGHT / 2){
//			System.out.println("bottom");
			xShift = player.getxPosition() - Main.WIDTH / 2;
			yShift = backGround.getHeight() - Main.HEIGHT;
		}
		// middle area
		else{
//			System.out.println("middle");
			xShift = player.getxPosition() - Main.WIDTH / 2;
			yShift = player.getyPosition() - Main.HEIGHT / 2;
		}
		cameraBounds = new Rectangle(xShift, yShift, Main.WIDTH, Main.HEIGHT);
		BufferedImage backGroundSection = backGround.getSubimage(xShift, yShift, Main.WIDTH, Main.HEIGHT);
		g.drawImage(backGroundSection, 0, 0, null);
		if(morphBall != null)
			morphBall.drawAt(g, morphBall.getxPosition() - xShift, morphBall.getyPosition() - yShift);
		for(Enemy e : level.getEnemies()){
			e.drawAt(g, e.getxPosition() - xShift, e.getyPosition() - yShift);
		}
		for(Wall w : level.getWalls()){
			//w.drawAt(g, w.getxPosition() - xShift, w.getyPosition() - yShift);
		}
		player.drawAt(g, player.getxPosition() - xShift, player.getyPosition() - yShift);
		if(player.isGunVisible())
			player.getGun().drawAt(g, player.getGun().getxPosition() - xShift, player.getGun().getyPosition() - yShift);
		
		for(Beam b : level.getBeams()){
			b.drawAt(g, b.getxPosition() - xShift, b.getyPosition() - yShift);
			// remove beams that are off screen
			if(b.getxPosition() - xShift < 0 || b.getxPosition() - xShift> Main.WIDTH || b.getyPosition() - yShift < 0 || b.getyPosition() - yShift > Main.HEIGHT)
				b.setToRemove(true);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		g.drawString("EN " + player.getEnergy(), 20, 80);
	}
}
