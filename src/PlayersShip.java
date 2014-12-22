import java.sql.ResultSet;
public class PlayersShip 
{
	private Utilities log; //log
	private DBManager dbm;
	public PlayersShip(DBManager dbm)
	{
		this.dbm = dbm;
		initTable();
	}
	public void initTable()
	{	
		//counterOfPlayers = 0;
		dbm.update( "CREATE TABLE IF NOT EXISTS ship (id integer, lvl int,  int, posCube int, destPlanet int, destSS int, destCube int)");
	}
}
