
public class VarShip {
	private Utilities log; //log
	private boolean busy;
	public int x, y,lvl, id, owner_id;
	public VarShip()
	{
		log = new Utilities();
	}
	public VarShip(int id,int owner_id,int lvl, int x, int y, boolean busy)
	{
		log = new Utilities();
		this.id = id;
		this.owner_id = owner_id;
		this.x = x;
		this.y = y;
		this.lvl = lvl;
		this.busy = busy;
	}
	public String toString()
	{
		return "Statek: "+id+" nalezy do gracza: "+owner_id+" \npozycja: ("+x+";"+y+") \npoziom statku: "+lvl+"\n zajety: "+busy;
	}
	public String toStringDeluxe(VarPlayer player)
	{
		return "Statek: "+id+" nalezy do gracza: "+player.getName()+" \n\tpozycja: ("+x+";"+y+")\n\tpoziom statku: "+lvl+"\n\t zajety: "+busy;
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
	public int getOwnerId() {
		return owner_id;
	}
}
