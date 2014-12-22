
public class Ship {
	private boolean busy;
	private int x, y,lvl, id;
	public Ship(int id,int lvl, int x, int y, boolean busy)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.lvl = lvl;
		this.busy = busy;
	}
	public String toString()
	{
		return "Statek gracza: "+id+" \npozycja: ("+x+";"+y+") \npoziom statku: "+lvl+"\n zajety: "+busy;
	}
	public int getId()
	{
		return id;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getLvl()
	{
		return lvl;
	}
	public boolean getBusy()
	{
		return busy;
	}
}
