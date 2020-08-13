import java.util.Iterator;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
enum Action
{
	run,
	jump,
	run_and_jump,
	wait;
}

class Mario extends Sprite
{
	int prev_x;
	int prev_y;
	
	double vvel;
	Model model;
	static BufferedImage[] mario_images = null;
	
	int airtime;
	coinblock cb;
	
	Json marshall()
	{
		Json ob = Json.newObject();
		ob.add("type", "mario");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("vvel", vvel);
		ob.add("prev_x", prev_x);
		ob.add("prev_y", prev_y);
		return ob;
	}
	
	Mario(Model m)
	{
		model = m;
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
			} 
			catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	Mario(Mario that, Model newmodel)
	{
		x = that.x;
		y = that.y;
		w = that.w;
		h = that.h;
		vvel = that.vvel;
		prev_x = that.prev_x;
		prev_y = that.prev_y;
		airtime = that.airtime;
		model = newmodel;
	}
	
	Mario cloneme(Model newmodel)
	{
		return new Mario(this, newmodel);
	}
	
	Mario(Json ob, Model m)
	{
		model = m;
		
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		vvel = (double)ob.getDouble("vvel");
		prev_x = (int)ob.getLong("prev_x");
		prev_y = (int)ob.getLong("prev_y");
	}
	
	void Memory()
	{
		prev_y = y;
	}
	
	String calltype()
	{
		return "Mario";
	}
	
	void get_out(int _x, int _y, int _w, int _h)
	{
		if(x + w >= _x && prev_x + w <= _x)
		{
			x = _x - w;
		}
		else if(x <= _x + _w && prev_x >= _x + _w)
		{
			x = prev_x;
		}
		else if(y + h >= _y && prev_y + h <= _y)
		{
			y = prev_y;
			vvel = 0;
			airtime = 0;
		}
		else if(y <= _y + _h && prev_y >= _y + _h)
		{
			y = prev_y;
			vvel = 0;
			airtime = 5;
		}
		
	}
	
	void paintme(int x, int y, int w, int h, Graphics g)
	{
		int marioframe = (Math.abs(x) / 20) % 5;
 		g.drawImage(mario_images[marioframe], x - model.scrollPos, y, null);
	}
	
	void update()
	{
		w = 60;
		h = 95;
		model.scrollPos = x - 222;
		Memory();
		vvel += 3.14159;
		y += vvel;
		airtime += 1;
		
		if(y >= 525)
		{
			y = 525;
			vvel = 0;
			airtime = 0;
		}
		
		
		// COLLISION ITERATOR
		Iterator<Sprite> it = model.sprites.iterator();
		while(it.hasNext())
		{
			Sprite s = it.next();
			
			if(s.calltype() == "Brick" || s.calltype() == "Coinblock")
			{
				if(doesCollidewith(s)){
					if (s.calltype() == "Coinblock" && y <= s.y + s.h && prev_y >= s.y + s.h){
//						System.out.println("Hit detected");
						s.hit = true; //I promise that was purely unintentional
					}
					get_out(s.x, s.y, s.w, s.h);
				}
			}
		}
		
		// AM I COLLIDING???
/* 		for(int i = 0; i < model.bricks.size(); i++)
		{
			Brick b = model.bricks.get(i);
			if(doesCollide(b.x, b.y, b.w, b.h)){
				get_out(b.x, b.y, b.w, b.h);
				
			}
		} */
	}
}