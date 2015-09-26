package engine;

import java.awt.event.KeyEvent;

public class Keyboard {
	public static boolean ESCAPE;
	public static boolean Z;
	public static boolean X;
	public static boolean LEFT;
	public static boolean RIGHT;
	public static boolean UP;
	public static boolean DOWN;
	public static boolean SHIFT;
	public static boolean ENTER;
	
	public static void setPressed(KeyEvent k){
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE){
			ESCAPE = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_Z){
			Z = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_X){
			X = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_LEFT){
			LEFT = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_RIGHT){
			RIGHT = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_UP){
			UP = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_DOWN){
			DOWN = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_SHIFT){
			SHIFT = true;
		}
		else if(k.getKeyCode() == KeyEvent.VK_ENTER){
			ENTER = true;
		}
	}
	
	public static void setReleased(KeyEvent k){
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE){
			ESCAPE = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_Z){
			Z = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_X){
			X = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_LEFT){
			LEFT = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_RIGHT){
			RIGHT = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_UP){
			UP = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_DOWN){
			DOWN = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_SHIFT){
			SHIFT = false;
		}
		else if(k.getKeyCode() == KeyEvent.VK_ENTER){
			ENTER = false;
		}
	}
}
