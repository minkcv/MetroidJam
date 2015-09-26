package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameOverMenu extends Interactive{

	private Main main;
	public GameOverMenu(Main main){
		this.main = main;
	}
	@Override
	public void update() {
		if(Keyboard.ENTER){
			main.startGame();
		}
		if(Keyboard.ESCAPE){
			System.exit(0);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		g.drawString("Game Over", 20, 80);
		g.drawString("Press enter for new game or esc to quit", 20, 140);
	}

}
