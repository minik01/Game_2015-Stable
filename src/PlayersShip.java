import java.sql.ResultSet;
import java.sql.SQLException;
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
		dbm.update( "CREATE TABLE IF NOT EXISTS ship (id integer, lvl int, current_x int, current_y int, busy boolean)");
	}
	public void save(Ship ship)
	{
		dbm.update("insert into ship values("+ship.getId()+",'"+ship.getLvl()+"','"+ship.getX()+"','"+ship.getY()+",'"+ship.getBusy()+"')");
	}
	public Ship getShip(int id)
	{
		ResultSet rs = dbm.select3("ship", id);
		try {
			return new Ship(id,rs.getInt("lvl"), rs.getInt("current_x"), rs.getInt("current_y"), rs.getBoolean("busy"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Ship(-1,-1, -1, -1, true);
	}
	public void example()
	{
		Ship ship2 = new Ship(1, 5, 99, 100, false);
		save(ship2);
	}
}

