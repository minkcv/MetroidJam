package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CreditsScreen extends Interactive {
	private BufferedImage creditsImage;
	private Main main;
	public CreditsScreen(Main main){
		this.main = main;
		try{
			creditsImage = ImageIO.read(getClass().getResource("/resources/fadeart/scroll.png"));			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		if(Keyboard.ESCAPE)
			System.exit(0);
		if(Keyboard.ENTER)
			main.startGame();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(creditsImage, 0, 0, null);
		g.setColor(Color.white);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		g.drawString("You have restored peace in the galaxy", 40, 100);
		g.setFont(new Font("Monospaced", Font.PLAIN, 14));
		g.drawString("A fan game by:", 80, 180);
		g.drawString("Will Smith (minkcv)", 90, 200);
		g.drawString("Jesse Ratt (beatscribe)", 90, 220);
	}
}
