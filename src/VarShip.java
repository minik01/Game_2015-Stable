
public class VarShip {
	private Utilities log; //log
	private boolean busy;
	private int x, y,lvl, id, ovnerId;
	public VarShip()
	{
		log = new Utilities();
	}
	public VarShip(int id,int ovnerId,int lvl, int x, int y, boolean busy)
	{
		log = new Utilities();
		this.id = id;
		this.ovnerId = ovnerId;
		this.x = x;
		this.y = y;
		this.lvl = lvl;
		this.busy = busy;
	}
	public String toString()
	{
		return "Statek: "+id+" należy do gracza: "+ovnerId+" \npozycja: ("+x+";"+y+") \npoziom statku: "+lvl+"\n zajety: "+busy;
	}
	public String toStringDeluxe(VarPlayer player)
	{
		return "Statek: "+id+" należy do gracza: "+player.getName()+" \n\tpozycja: ("+x+";"+y+")\n\tpoziom statku: "+lvl+"\n\t zajety: "+busy;
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
	public int getOvnerId() {
		return ovnerId;
	}
}
