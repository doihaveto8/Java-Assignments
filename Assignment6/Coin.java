import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
class Coin extends Sprite
{
	Model model;
	double vvel = -9.0;
	double hvel;
	static BufferedImage coin = null;
	
	Coin(int _x, int _y, double _hvel, Model m)
	{
		x = _x;
		y = _y;
		hvel = _hvel;
		
		if(coin == null)
		{
			try
			{
				coin = ImageIO.read(new File("coin.png"));
			} 
			catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	Coin(Coin coin, Model newmodel)
	{
		x = coin.x;
		y = coin.y;
		hvel = coin.hvel;
		model = newmodel;
	}
	
	Coin cloneme(Model newmodel)
	{
		return new Coin(this, newmodel);
	}
	
	Coin(Json ob, Model m)
	{
		model = m;
		
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		hvel = (double)ob.getDouble("hvel");
		
		if(coin == null)
		{
			try
			{
				coin = ImageIO.read(new File("Coin.png"));
			} 
			catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
		ob.add("type", "Coin");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("hvel", hvel);
		return ob;
	}
	
	String calltype()
	{
		return "Coin";
	}
	
	void paintme(int x, int y, int w, int h, Graphics g)
	{
 		g.drawImage(coin, x, y, null);
	}
	
	void update()
	{
		x += hvel;
		vvel += 3.14159;
		y += vvel;
		
		if(y >= 600)
			done = true;
	}
}