package gameElements;

import java.awt.Rectangle;

public class MusicChanger extends Sprite{
	private int music;
	public MusicChanger(int x, int y, int music) {
		super(x, y, 0, 1, 1);
		this.music = music;
		stepWidth = 32 * 6;
		stepHeight = 32;
		bounding = new Rectangle(xPosition, yPosition, stepWidth, stepHeight);
	}
	
	public int getMusic(){
		return music;
	}
}
