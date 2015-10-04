package gameElements;

import java.awt.Rectangle;

public class MusicChanger extends Sprite{

	public MusicChanger(int x, int y) {
		super(x, y, 0, 1, 1);
		stepWidth = 32 * 6;
		stepHeight = 32;
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
	}
}
