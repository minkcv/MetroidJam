/*
 * Will Smith
 */

package engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class GameCanvas extends Canvas implements KeyListener, FocusListener{
	
	private Main main;
	private BufferedImage bufferData;
	private Graphics g;
	private int width;
	private int height;
	
	public GameCanvas(Main main){		
		this.main = main;
		width = main.WIDTH;
		height = main.HEIGHT;
		setPreferredSize(new Dimension(width, height));
		
		addKeyListener(this);
		addFocusListener(this);
	}

	//this is called when repaint is called. you do NOT also need a paint method
	public void update(Graphics screen){
		if (bufferData == null)
		{
			bufferData = (BufferedImage)createImage(getWidth(), getHeight());
			g = bufferData.getGraphics();
		}
		
		main.draw(g);
		screen.drawImage(bufferData, 0, 0, null);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		main.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		main.keyReleased(e);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
