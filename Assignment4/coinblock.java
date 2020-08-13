import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
class coinblock extends Sprite
{
	Model model;
	Coin coin;
	int coins;
	int blockphase = 0;
	
	static BufferedImage coinblock_images[] = null;
	
	coinblock(int _x, int _y, int c, Model m)
	{
		model = m;
		x = _x;
		y = _y;
		coins = c;
		
		if(coinblock_images == null)
		{
			coinblock_images = new BufferedImage[2];
			
			try
			{
				coinblock_images[0] = ImageIO.read(new File("block1.png"));
				coinblock_images[1] = ImageIO.read(new File("block2.png"));
			} 
			catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	coinblock(Json ob, Model m)
	{
		model = m;
		
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		coins = (int)ob.getLong("Coins");
		
		if(coinblock_images == null)
		{
			coinblock_images = new BufferedImage[2];
			
			try
			{
				coinblock_images[0] = ImageIO.read(new File("block1.png"));
				coinblock_images[1] = ImageIO.read(new File("block2.png"));
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
		ob.add("type", "coinblock");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("Coins", coins);
		return ob;
	}
	
	String calltype()
	{
		return "Coinblock";
	}
	
	void paintme(int x, int y, int w, int h, Graphics g)
	{
 		g.drawImage(coinblock_images[blockphase], x, y, null);
	}
	
	void makecoin()
	{
		if(coins < 5){
			coins++;
			coin = new Coin(x, y - h, Math.random() * (15.0 + 15.0) - 15.0, model);
			model.sprites.add(coin);
		}
		if(coins == 5)
			blockphase = 1;
		else
		
		hit = false;
	}
	
	void update()
	{
		w = 89;
		h = 83;
		
		if(hit)
			makecoin();
	}
}