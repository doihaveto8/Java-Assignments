import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean space;
	int mousedownx;
	int mousedowny;
	
	void setView(View v)
	{
		view = v;
	}
	
	Controller(Model m)
	{
		model = m;
	}

	public void actionPerformed(ActionEvent e)
	{
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_S: model.save("map.json"); break;
			case KeyEvent.VK_L: model.load("map.json"); break;
			case KeyEvent.VK_SPACE: space = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: space = false; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void update()
	{	
	// The collision detection for the left and rights sides of bricks
	// wouldn't work until i set prev_x to update here instead of with
	// prev_y
	
		if(keyRight){
				model.mario.prev_x = model.mario.x;
				model.mario.x += 10;
		}
		
		if(keyLeft){
				model.mario.prev_x = model.mario.x;
				model.mario.x -= 10;
		}
		
		if(space && model.mario.airtime < 5){
			model.mario.vvel -= 10.1;
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		//model.setDestination(e.getX(), e.getY());
		mousedownx = e.getX();
		mousedowny = e.getY();
	}

	public void mouseReleased(MouseEvent e) 
	{
		int x1 = mousedownx;
		int x2 = e.getX();
		int y1 = mousedowny;
		int y2 = e.getY();
		int left = Math.min(x1, x2);
		int right = Math.max(x1, x2);
		int top = Math.min(y1, y2);
		int bottom = Math.max(y1, y2);
		
		model.addBrick(left + model.scrollPos, top, right - left, bottom - top);
	}

	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e)
	{
		if(e.getY() < 100)
		{
			System.out.println("break here");
		}
	}
}
