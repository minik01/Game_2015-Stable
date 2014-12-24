public class VarPlayer {
	PlayersTech tech;
	VarShip ship;
	VarPlayer(DBManager dbm)
	{
		//position = new PlayersPosition(dbm);
		ship = new VarShip();
		//tech = new PlayersTech(dbm);
	}
	public PlayersTech getTech()
	{
		return tech;
	}
	public void create()
	{
		
	}
	public VarShip getShip()
	{
		return ship;
	}
}
