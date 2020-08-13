import java.util.ArrayList;
import java.awt.Graphics;
class Model
{
	Mario mario;
	coinblock cb;
	ArrayList<Sprite> sprites;
	Graphics g;
	
	int scrollPos;
	
	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
	}

	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			s.update();
			
			if(s.done == true)
				sprites.remove(s);
		}
	}
	
/* 	void addBrick(int x, int y, int w, int h)
	{
		Brick b = new Brick(x - scrollPos, y, w, h);
		sprites.add(b);
	} */
	
	void unmarshall(Json ob)
	{
		sprites.clear();
		Json json_sprites = ob.get("sprites");
		for(int i = 0; i < json_sprites.size(); i++)
		{
			
			Json j = json_sprites.get(i);
			String s = j.getString("type");
			Sprite spr;
			if(s.equals("mario")){
				mario = new Mario(j, this);
				sprites.add(mario);
			}
			else if(s.equals("coinblock")){
				cb = new coinblock(j, this);
				sprites.add(cb);
			}
			else{
				spr = new Brick(j);
				sprites.add(spr);
			}
		}
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
		Json json_sprites = Json.newList();
		ob.add("sprites", json_sprites);
		
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			Json j = s.marshall();
			json_sprites.add(j);
		}
		return ob;
	}
	
	void save(String filename)
	{
		Json ob = marshall();
		ob.save(filename);
	}
	
	void load(String filename)
	{
		Json ob = Json.load(filename);
		unmarshall(ob);
	}
}
