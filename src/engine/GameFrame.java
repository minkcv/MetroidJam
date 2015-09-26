package engine;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GameFrame extends JFrame implements KeyListener, FocusListener
{
	private Main main;
	public GameFrame(int width, int height, Main main, String title)
	{
		this.main = main;
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		main.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		main.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
