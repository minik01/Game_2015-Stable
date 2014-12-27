public class VarPlayer {
	
	private PlayersTech tech;
	private VarShip ship;		
	private Utilities log;
	private int id;
	private DBManager dbm;
	
	public VarPlayer(DBManager dbm)
	{
		this.dbm = dbm;
		log = new Utilities();
		//position = new PlayersPosition(dbm);
		//ship = new VarShip();
		//tech = new PlayersTech(dbm);
	}
	public PlayersTech getTech()
	{
		return tech;
	}
	public void create(int id)
	{
		dbm.init_test();
		WrapperShip wp = new WrapperShip(dbm);
		this.id = id;
		int shipId = wp.getCounterOfShip();
		ship = new VarShip(shipId, id,1, log.start_x, log.start_y, false);
		
	}
	public VarShip getShip()
	{
		if(ship == null){
			System.out.println("error - void VarShip");
			return new VarShip(-1,-1,-1,-1,-1,true); 
		} 
		return ship;
	}
}
