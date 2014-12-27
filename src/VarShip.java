
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
	public int getOvnerId() {
		return ovnerId;
	}
}
