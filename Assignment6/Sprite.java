import java.awt.Graphics;
abstract class Sprite
{
	int x;
	int y;
	int w;
	int h;
	boolean done;
	boolean hit;
	
	abstract void update();
	
	abstract String calltype();
	
	abstract void paintme(int x, int y, int w, int h, Graphics g);
	
	abstract Json marshall();
	
	abstract Sprite cloneme(Model newmodel);
	
	boolean doesCollidewith(Sprite that)
	{
		if(this.x + this.w <= that.x)
			return false;
		if(this.x >= that.x + that.w)
			return false;
		if(this.y + this.h <= that.y)
			return false;
		if(this.y >= that.y + that.h)
			return false;
		else
			return true;
		
	}
}