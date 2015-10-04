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
	private BufferedImage fade4;
	private BufferedImage fade3;
	private BufferedImage fade2;
	private BufferedImage fade1;
	private BufferedImage fadein1;
	private BufferedImage fadein2;
	private BufferedImage fadein3;
	private BufferedImage fadein4;
	private BufferedImage full;
	private BufferedImage scroll;
	private BufferedImage currentImage;
	private BufferedImage[] images;
	private enum Current{
		F1, F2, F3, F4, FI1, FI2, FI3, FI4, MAIN, SCROLL
	}
	private boolean fadein;
	private Current current;
	private int titleX;
	private int moveSpeed = 2;
	private long waitStartTime;
	private long waitTime;
	private long fadeTime = 500;
	private long readTime = 5000;
	private long scrollTime = 0;
	private boolean titleThemePlayed;
	public TitleMenu(Main main){
		this.main = main;
		try {
			fade4 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fade4.png"));
			fade3 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fade3.png"));
			fade2 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fade2.png"));
			fade1 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fade1.png"));
			fadein1 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fadein1.png"));
			fadein2 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fadein2.png"));
			fadein3 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fadein3.png"));
			fadein4 = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/fadein4.png"));
			full = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/main.png"));
			scroll = ImageIO.read(Main.class.getResourceAsStream("/resources/fadeart/scroll.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		images = new BufferedImage[10];
		images[0] = fade1;
		images[1] = fade2;
		images[2] = fade3;
		images[3] = fade4;
		images[4] = fadein1;
		images[5] = fadein2;
		images[6] = fadein3;
		images[7] = fadein4;
		images[8] = full;
		images[9] = scroll;
		currentImage = fade4;
		current = Current.F4;
		waitStartTime = System.currentTimeMillis();
		waitTime = fadeTime;
		fadein = true;
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
			switch(current){
			case F4:
				if(fadein){
					current = Current.F3;
					waitTime = fadeTime;
				}
				else{
					current = Current.SCROLL;
					waitTime = fadeTime;
				}
				break;
			case F3:
				if(fadein){
					current = Current.F2;
					waitTime = fadeTime;
				}
				else{
					current = Current.F4;
					waitTime = fadeTime;
				}
				break;
			case F2:
				if(fadein){
					current = Current.F1;
					waitTime = fadeTime;
				}
				else{
					current = Current.F3;
					waitTime = fadeTime;
				}
				break;
			case F1:
				if(fadein){
					current = Current.MAIN;
					waitTime = readTime;
				}
				else{
					current = Current.F2;
					waitTime = fadeTime;
				}
				break;
			case FI1:
				current = Current.FI2;
				waitTime = fadeTime;
				break;
			case FI2:
				current = Current.FI3;
				waitTime = fadeTime;
				break;
			case FI3:
				current = Current.FI4;
				waitTime = fadeTime;
				break;
			case FI4:
//				current = Current.MAIN;
				waitTime = readTime;
				break;
			case SCROLL:
				waitTime = scrollTime;
				break;
			case MAIN:
				if(fadein){
					current = Current.F1;
					waitTime = fadeTime;
				}
				else{
					current = Current.F4;
					waitTime = fadeTime;
					titleX = 0;
				}
				fadein = ! fadein;
				break;
			}
			waitStartTime = System.currentTimeMillis();
			currentImage = images[current.ordinal()];
//			System.out.println(current);
			if(waitTime == scrollTime){
				titleX -= moveSpeed;
				if(Math.abs(titleX) > scroll.getWidth() / 2){
					waitTime = fadeTime;
					current = Current.FI1;
				}
//				if(titleX == 0 || titleX == -title2Image.getWidth() / 4 || titleX == -title2Image.getWidth() / 2 || -titleX == title2Image.getWidth() * (3/4.)){
//					waitStartTime = System.currentTimeMillis();
//				}
//				if(titleX < -title2Image.getWidth() / 2){
//					titleX = 0;
//				}
			}
		}
	}

	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage, titleX, 0, null);
		if(current.ordinal() < 4 || current == Current.MAIN){
			g.setColor(Color.white);
			g.setFont(new Font("Monospaced", Font.PLAIN, 24));
			g.drawString("Press Enter", Main.WIDTH / 2 - 90, 300);
		}
	}
}
