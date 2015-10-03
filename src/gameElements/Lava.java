package gameElements;

import java.awt.Rectangle;

public class Lava extends Sprite {

	public Lava(int x, int y) {
		super(x, y, 0, 1, 1);
		stepWidth = 32;
		stepHeight = 32;
		bounding = new Rectangle(x, y, stepWidth, stepHeight);
	}
}
