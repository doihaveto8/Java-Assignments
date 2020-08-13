import java.util.Iterator;
class Mario
{
	int prev_x;
	int prev_y;
	
	int x;
	int y;
	int w = 60;
	int h = 95;
	double vvel;
	Model model;
	
	int airtime;
	
	Mario(Model m)
	{
		model = m;
	}
	
	void Memory()
	{
		prev_y = y;
	}
	
	boolean doesCollide(int _x, int _y, int _w, int _h)
	{
		if((x + w) <= _x)
			return false;
		else if(x >= (_x + _w))
			return false;
		else if((y + h) <= _y)
			return false;
		else if(y >= (_y + _h))
			return false;
		else
			return true;
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
		}
		
	}
	
	void update()
	{
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
		Iterator<Brick> it = model.bricks.iterator();
		while(it.hasNext())
		{
			Brick b = it.next();
			
			if(doesCollide(b.x, b.y, b.w, b.h)){
				get_out(b.x, b.y, b.w, b.h);
				
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