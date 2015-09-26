package engine;

import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
	public static final int WIDTH = 512;
	public static final int HEIGHT = 480;
	private Game game;
	private GameFrame gFrame;
	private GameCanvas gCanvas;
	private TitleMenu titleMenu;
	private PauseMenu pauseMenu;
	private Interactive currentInteractive;
	private GameState gState;
	private enum GameState {
		GAME, TITLE_MENU, PAUSE_MENU
	}
	
	public static void main(String[] args){ new Main(); }
	private Main(){
		Sounds.loadSounds();
		game = new Game(this);
		titleMenu = new TitleMenu(this);
		pauseMenu = new PauseMenu(this);
		gFrame= new GameFrame(WIDTH, HEIGHT, this, "MTRD");
		gCanvas= new GameCanvas(this);
		gFrame.add(gCanvas);
		gFrame.pack();
		gCanvas.requestFocus();
		gState = GameState.TITLE_MENU;
		currentInteractive = titleMenu;
		gameLoop();
	}
	
	private void gameLoop(){
		while(true){
			switch(gState){
			case GAME:
				currentInteractive = game;
				break;
			case TITLE_MENU:
				currentInteractive = titleMenu;
				break;
			case PAUSE_MENU:
				currentInteractive = pauseMenu;
				break;
			}
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			currentInteractive.update();
			gCanvas.repaint();
		}
	}
	
	public void draw(Graphics g){
		currentInteractive.draw(g);
	}
	
	public void startGame(){
		gState = GameState.GAME;
	}
	
	public void pauseGame(){
		gState = GameState.PAUSE_MENU;
	}
	
	public void unPauseGame(){
		gState = GameState.GAME;
	}

	public void focusGained(FocusEvent e) {
		
	}
	public void focusLost(FocusEvent e) {
		
	}
	public void keyPressed(KeyEvent e) {
		Keyboard.setPressed(e);
	}
	public void keyReleased(KeyEvent e) {
		Keyboard.setReleased(e);
	}
}
