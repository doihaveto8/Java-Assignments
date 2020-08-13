import java.util.ArrayList;
import java.awt.Graphics;
class Model
{
	Mario mario;
	coinblock cb;
	ArrayList<Sprite> sprites;
	Graphics g;
	
	int scrollPos;
	int coins = 0;
	int jumps = 0;
	
	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
	}
	
	Model(Model that)
	{
		mario = new Mario(that.mario, that);
		cb = new coinblock(that.cb, that);
		sprites = new ArrayList<Sprite>();
		coins = that.coins;
		jumps = that.jumps;
		for(int i = 0; i < that.sprites.size(); i++)
		{
			Sprite other = that.sprites.get(i);
			Sprite clone = other.cloneme(this);
			sprites.add(clone);
			if(clone.calltype() == "Mario")
				mario = (Mario)clone;
		}
		g = that.g;
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
	
	double evaluateAction(Action action, int depth)
	{
	// Evaluate the state
	if(depth >= 25)
	{
		return mario.x + 5000 * coins - 200 * jumps;
	}

	// Simulate the action
	Model copy = new Model(this); // uses the copy constructor
	copy.doAction(action); // like what Controller.update did before
	copy.update(); // advance simulated time

	// Recurse
	if(depth % 6 != 0)
	   return copy.evaluateAction(action, depth + 1);
	else
	{
	   double best = copy.evaluateAction(Action.run, depth + 1);
	   best = Math.max(best, copy.evaluateAction(Action.jump, depth + 1));
	   best = Math.max(best, copy.evaluateAction(action.run_and_jump, depth + 1));
	   return best;
	}
	}
	
	void doAction(Action i)
	{
		if(i == Action.run)
		{
			mario.prev_x = mario.x;
			mario.x += 10;
			if(mario.x <= mario.prev_x + 5)
				doAction(Action.jump);
		}
		else if(i == Action.jump)
		{
			jumps++;
			mario.vvel = -18.2;
		}
 		else if(i == Action.run_and_jump)
		{
			jumps++;
			mario.prev_x = mario.x;
			mario.x += 10;
			mario.vvel = -18.2;
		}
		else if(i == Action.wait)
		{
		}
	}
}
