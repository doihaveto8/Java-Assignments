import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller extends Object implements ActionListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean space;
	boolean loaded;
	int initialize = 0;
	double runcheck; //to make sure Mario won't run if his score wont increase
	double jumpcheck; //to make sure Mario won't jump if his score wont increase
	boolean no_change_in_run;
	boolean no_change_in_jump;
	
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
			//model.mario.jumps++;
		}
		
		if(!loaded){
			model.load("map.json");
			loaded = true;
		}
		
		// Evaluate each possible action
		
		double score_run = model.evaluateAction(Action.run, 0);
		double score_jump = model.evaluateAction(Action.jump, 0);
		if(score_run == runcheck && initialize != 0)
			no_change_in_run = true;
		else
			no_change_in_run = false;
		runcheck = score_run;
		
		/* if(score_jump < jumpcheck && initialize != 0)
			no_change_in_jump = true;
		else
			no_change_in_jump = false;
		jumpcheck = score_jump; */
		double score_run_and_jump = model.evaluateAction(Action.run_and_jump, 0);

		// Do the best one
		if(score_run_and_jump > score_jump && score_run_and_jump > score_run)
		{
			//System.out.println("Run and Jump");
			//System.out.println(score_run_and_jump);
			if(model.mario.airtime >= 5)
				model.doAction(Action.run);
			else
				model.doAction(Action.run_and_jump);			
		}
		else if(score_jump > score_run)
		{
			//System.out.println("Jump");
			//System.out.println(score_jump);
			/* if(!no_change_in_jump)
			{ */
				if(model.mario.airtime >= 5)
					model.doAction(Action.wait);
				else
					model.doAction(Action.jump);
			/* }
			else
				model.doAction(Action.run); */
		}
		else
		{
			if(no_change_in_run) //In the event that Score_run doesn't change in 2 cycles, Mario jumps instead
				model.doAction(Action.jump);
			else
			{
				System.out.println("Run");
				System.out.println(score_run);
				model.doAction(Action.run);
			}
		}
		
		initialize = 1;
	}
	
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e) 
	{
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
