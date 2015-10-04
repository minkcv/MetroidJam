package gameElements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Spikes extends Enemy{

	public Spikes(int x, int y) {
		super(x, y, 0, 1, 1, Integer.MAX_VALUE, 5);
		stepWidth = 32;
		stepHeight = 32;
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
	}

	@Override
	public void update(Player player, ArrayList<Wall> walls) {
		
	}
	
	@Override
	public void hit(int damage){
		// intentionally do nothing
	}
	@Override
	public void drawAt(Graphics g, int x, int y){
		// intentionally do nothing
	}
}
