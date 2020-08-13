import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
public class Brick extends Sprite
{	
	Brick(int _x, int _y, int _w, int _h)
	{
		x = _x;
		y = _y;
		w = _w;
		h = _h;
	}
	
	Brick(Json ob)
	{
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
		ob.add("type", "brick");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		return ob;
	}
	
	String calltype()
	{
		return "Brick";
	}
	
	void paintme(int _x, int _y, int _w, int _h, Graphics g)
	{
		g.setColor(new Color(0, 0, 0));
		g.drawRect(_x, _y, _w, _h);
		g.fillRect(_x, _y, _w, _h);
	}
	
	void update()
	{
	}
}