package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameElements.Player;

public class HUD {
	private Player player;
	private BufferedImage emptyTank;
	private BufferedImage fullTank;
	private BufferedImage missileIcon;
	public HUD(Player player){
		this.player = player;
		loadGraphics();
	}
	
	public void loadGraphics(){
		try {
			emptyTank = ImageIO.read(getClass().getResource("/resources/etankEmpty.png"));
			fullTank = ImageIO.read(getClass().getResource("/resources/etankFull.png"));
			missileIcon = ImageIO.read(getClass().getResource("/resources/missileicon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		if(player.getEnergyCapacity() > 100){
			int eTanks = player.getEnergyCapacity() / 100;
			int fullETanks = player.getEnergy() / 100;
			int emptyETanks = eTanks - fullETanks;
			for (int i = 0; i < fullETanks; i++) {
				g.drawImage(fullTank, 96 + 40 * (i + 1), 30, null);
			}
			for (int i = 0; i < emptyETanks; i++) {
				g.drawImage(emptyTank, 96 + fullETanks * 40 + 40 * (i + 1), 30, null);
			}
		}
		g.drawString("EN " + player.getEnergy() % 100, 20, 60);			
		if(player.hasMissiles()){
			g.drawString(player.getMissileCount() + "", 68, 80);
			g.drawImage(missileIcon, 20, 60, null);
		}
	}
}
