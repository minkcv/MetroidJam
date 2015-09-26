package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TitleMenu extends Interactive{
	private Main main;
	private BufferedImage title2Image;
	private int titleX;
	private int moveSpeed = 2;
	private long waitStartTime;
	private long waitTime = 10000;
	private boolean titleThemePlayed;
	public TitleMenu(Main main){
		this.main = main;
		try {
			title2Image = ImageIO.read(Main.class.getResourceAsStream("/resources/title2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		waitStartTime = System.currentTimeMillis();
	}
	
	@Override
	public void update(){
		if(Keyboard.ESCAPE)
			System.exit(0);
		
		if(! titleThemePlayed){
			Sounds.playTitle();
			titleThemePlayed = true;
		}
		if(Keyboard.ENTER){
			Sounds.stopTitle();
			main.startGame();
		}
		if(waitStartTime + waitTime < System.currentTimeMillis()){
			titleX -= moveSpeed;
			if(titleX == 0 || titleX == -title2Image.getWidth() / 4 || titleX == -title2Image.getWidth() / 2 || -titleX == title2Image.getWidth() * (3/4.)){
				waitStartTime = System.currentTimeMillis();
			}
			if(titleX < -title2Image.getWidth() / 2){
				titleX = 0;
			}
		}
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(title2Image, titleX, 0, null);
//		g.setColor(Color.white);
//		g.setFont(new Font("Monospaced", Font.PLAIN, 24));
//		g.drawString("Press Enter", Main.WIDTH / 2 - 90, 300);
	}
}
