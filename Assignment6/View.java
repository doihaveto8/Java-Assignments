import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	Model model;
	BufferedImage background;
	boolean lazyload = true;
	
	View(Controller c, Model m)
	{
		if(lazyload = true)
		{
			lazyload = false;
			
			try
			{
				background = ImageIO.read(new File("Levelbackground.png"));
			} 
			catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
		model = m;
	}
	
	public void paintComponent(Graphics g)
	{
		// Clear screen
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(background, ((model.scrollPos / 2) * -1) - 120, 0, null);
		
		// Draw ground
		g.setColor(new Color(255, 255, 255));
		g.drawLine(0, 615, 2971, 615);
		
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			if(s.calltype() != "Mario")
				s.paintme(s.x - model.scrollPos, s.y, s.w, s.h, g);
			else
				s.paintme(s.x, s.y, s.w, s.h, g);
		}
		

	}
	
}
