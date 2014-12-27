import java.sql.ResultSet;
import java.sql.SQLException;
public class WrapperShip 
{
	private Utilities log; //log
	private DBManager dbm;
	private int counterOfShip;
	public WrapperShip(DBManager dbm)
	{
		log = new Utilities();
		this.dbm = dbm;
		initTable();
	}
	public void clear()
	{
		log.print('a', "table ship will be DELETED!");
	 	dbm.update("drop table if exists ship");
		initTable();
	}
	public void initTable()
	{	
		counterOfShip = 0;
		dbm.update( "CREATE TABLE IF NOT EXISTS ship (id integer,ovnerId integer, lvl int, current_x int, current_y int, busy boolean)");
	}
	public void save(VarShip ship)
	{
		dbm.update("insert into ship values("+ship.getId()+","+ship.getOvnerId()+",'"+ship.getLvl()+"','"+ship.getX()+"','"+ship.getY()+",'"+ship.getBusy()+"')");
	}
	public VarShip getShip(int id)
	{
		ResultSet rs = dbm.select3("ship", id);
		try {
			return new VarShip(id,rs.getInt("ovnerId"),rs.getInt("lvl"), rs.getInt("current_x"), rs.getInt("current_y"), rs.getBoolean("busy"));
		} catch (SQLException e) 
		{
			System.out.println("ERROR - PlayersShip - getShip()");
		}
		return new VarShip(-1,-1,-1, -1, -1, true);
	}
	public void example()
	{
		VarShip ship2 = new VarShip(1, 1, 5, 99, 100, false);
		save(ship2);
	}
	public void printAll()
	{
		log.print('d',"====================================Statki================================");
		counterOfShip = 0;
		try{
			ResultSet rs = dbm.select2("select * from ship");
			while(rs.next())
			{
				// read the result set
				if(log.logDB)
				{
					log.print('d',"+");
					log.print('d',"+ id = " + rs.getInt("id"));
					log.print('d',"+ ownerId = " + rs.getInt("ownerId"));
					log.print('d',"+ lvl = " + rs.getInt("lvl"));
					log.print('d',"+ x = " + rs.getInt("x"));
					log.print('d',"+ y = " + rs.getInt("y"));
					log.print('d',"+ x = " + rs.getInt("x"));
					log.print('d',"+ busy = " + rs.getBoolean("busy"));
				}
				counterOfShip++;
			}
			log.print('d',"==========================================================================");
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in WrapperShip.printAll()");
		}
		log.print('d',"Ships in DB:"+ counterOfShip);
    }
	public int getCounterOfShip()
	{
		printAll();
		return counterOfShip;
	}
}

