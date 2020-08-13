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
	static BufferedImage[] mario_images = null;
	BufferedImage background;
	int anim_pic;
	
	View(Controller c, Model m)
	{
		if(mario_images == null)
		{
			mario_images = new BufferedImage[5];
			
			try
			{
				mario_images[0] = ImageIO.read(new File("mario1.png"));
				mario_images[1] = ImageIO.read(new File("mario2.png"));
				mario_images[2] = ImageIO.read(new File("mario3.png"));
				mario_images[3] = ImageIO.read(new File("mario4.png"));
				mario_images[4] = ImageIO.read(new File("mario5.png"));
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
		
		for(int i = 0; i < model.bricks.size(); i++)
		{
			Brick b = model.bricks.get(i);
			g.drawRect(b.x - model.scrollPos, b.y, b.w, b.h);
			g.fillRect(b.x - model.scrollPos, b.y, b.w, b.h);
		}
		
		int marioframe = (Math.abs(model.mario.x) / 20) % 5;
 		g.drawImage(mario_images[marioframe], model.mario.x - model.scrollPos, model.mario.y, null);
	}
}
