import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBManager2 {
	private Utilities log; //log
	private DBManager dbm;
	public DBManager2()
	{
		dbm = new DBManager();
		log = new Utilities();
	}
	public Connection getConnection() {
		
		return dbm.getConnection();
	}
	public void closeConnection() {
		dbm.close();
	}
	public void clear()
	{
		log.print('!', "All tables will be DELETED!");
	 	dbm.update("drop table if exists players");
	 	dbm.update("drop table if exists ship");
		initTables();
	}
	public void initTables()
	{
		log.print('d', "Tworzenie tabeli graczy:");
		log.print('d', "CREATE TABLE IF NOT EXISTS players (id integer, username string, password string, mail string, points int, banned boolean)");
		dbm.update( "CREATE TABLE IF NOT EXISTS players (id integer, username string, password string, mail string, points int, banned boolean)");
		
		log.print('d', "Tworzenie tabeli statkow:");
		log.print('d', "CREATE TABLE IF NOT EXISTS ship (id integer,ovnerId integer, lvl int, current_x int, current_y int, busy boolean)");
		dbm.update( "CREATE TABLE IF NOT EXISTS ship (id integer,ovnerId integer, lvl int, current_x int, current_y int, busy boolean)");
	}
	
	
	// Statek
	public void addShip(VarShip ship)
	{
		dbm.update("insert into ship values("+ship.getId()+","+ship.getOvnerId()+","+ship.getLvl()+","+ship.getX()+","+ship.getY()+",'"+ship.getBusy()+"')");	//nie zapisuje do bazy danych
		printAllShip();
		//przydaloby sie sprawdzic, Id nie jest zajete...
	}
	public VarShip getShipById(int id)
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
	public ArrayList<VarShip> getShipsBySector(int x, int y, int r)		//?
	{
		ArrayList<VarShip> ships =new ArrayList<VarShip>();
		int xMinusR = x-r;
		int xPlusR = x+r;
		int yMinusR = y-r;
		int yPlusR = y+r;
		int i =0;
		log.print('d', "select * from ship WHERE current_x >"+xMinusR+" and current_x <"+xPlusR+" and current_y >"+yMinusR+" and current_y <"+yPlusR+" )");
		ResultSet rs = dbm.select2("select * from ship WHERE current_x >"+xMinusR+" and current_x <"+xPlusR+" and current_y >"+yMinusR+" and current_y <"+yPlusR+" )");
		try {
			while(rs.next())
			{
				VarShip ship = new VarShip(rs.getInt("id"),rs.getInt("ovnerId"),rs.getInt("lvl"), rs.getInt("current_x"), rs.getInt("current_y"), rs.getBoolean("busy"));
				ships.add(ship);
			}
			return ships;
		} catch (SQLException e) 
		{
			System.out.println("ERROR - PlayersShip - getShipsBySector()");
		}
		return null;
	}
	public void setShip(VarShip ship)
	{
		dbm.update("update ship set lvl = "+ship.getLvl()+",current_x = "+ship.getX()+",current_y = "+ship.getY()+",busy = '"+ship.getBusy()+"' where id="+ship.getId()+ ";");
	}
	public int numberOfShips()
	{
		int counterOfShips = 0;
		try{
			ResultSet rs = dbm.select2("select * from ship");
			while(rs.next())
				{ 
					counterOfShips++;
				}
			}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in Player.numberOfShips()");
		}
		return counterOfShips;
	}
	public int printAllShip()
	{
		log.print('d',"====================================Ships=================================");
		int counterOfShips = 0;
		try{
			ResultSet rs = dbm.select2("select * from ship");
			while(rs.next())
			{
				log.print('d',"+");
				log.print('d',"+ id = \t" + rs.getInt("id"));
				log.print('d',"+ ownerId = \t" + rs.getString("ovnerId"));
				log.print('d',"+ current_x = \t" + rs.getInt("current_x"));
				log.print('d',"+ current_y = \t" + rs.getInt("current_y"));
				log.print('d',"+ busy = \t" + rs.getBoolean("busy"));
				counterOfShips++;
			}
			log.print('d',"==========================================================================");
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in Player.printAll()");
		}
		log.print('d',"Ships in DB:"+ counterOfShips);
		return counterOfShips;
	}

	// Gracz
	public String addPlayer(VarPlayer player)
	{
		boolean correct = false;
		log.print('f', "check name: "+player.getName());
		correct = player.CheckName(player.getName());
		log.print('f', "ok");

		if(correct==true)
		{
			if(getPlayerByName(player.getName())==null)
			{
				Constant constant = new Constant();
				int counter =printAllPlayers();
				log.print('d',"insert into players (id, username, password, mail, points, banned) values("+counter+",'"+player.getName()+"','"+player.getPassword()+"','"+player.getMail()+"',0, 'false')");	
				dbm.update("insert into players (id, username, password, mail, points, banned) values("+counter+",'"+player.getName()+"','"+player.getPassword()+"','"+player.getMail()+"',0, 'false')");	
				player.setID(counter);
				addShip(new VarShip(printAllShip(), counter, 1, constant.default_start_x, constant.default_start_y, false));
				return "Your account has been created successfully";
			}
			else return "This Username is already taken";
		} 
		else
		{
			return "<b>You used illegal characters</b>. <br>Username should consist of alphanumeric values. <br>It may be also _ and -.<br>Length should be minimum 3 and maximum 15.";
		}
	}
	public VarPlayer getPlayerByName(String name)
	{
		ResultSet rs = dbm.select2("select * from players WHERE username='"+name+"'");
		try {
			VarPlayer player = new VarPlayer(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("mail"));
			return player;
		} catch (SQLException e) {
			return null;
		}
	}
	public VarPlayer getPlayerById(int id)	
	{
		ResultSet rs = dbm.select2("select * from players WHERE id="+id);
		try {
			VarPlayer player = new VarPlayer(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("mail"));
			return player;
		} catch (SQLException e) {
			return null;
		}
	}
	public boolean allowLogin(String login,String psswd)
	{
		if(login == null || psswd == null) return false;
		if(getPlayerByName(login)!=null)
			if( getPlayerByName(login).getPassword().equals(psswd) )	
			{
				return true;
			}
		else return false;
		return false;
	}
	
	public int printAllPlayers()
	{
		log.print('d',"====================================Gracze================================");
		int counterOfPlayers = 0;
		try{
			ResultSet rs = dbm.select2("select * from players");
			while(rs.next())
			{
				log.print('d',"+");
				log.print('d',"+ id = " + rs.getInt("id"));
				log.print('d',"+ username = " + rs.getString("username"));
				log.print('d',"+ password = " + rs.getString("password"));
				log.print('d',"+ e-mail = " + rs.getString("mail"));
				counterOfPlayers++;
			}
			log.print('d',"==========================================================================");
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in Player.printAll()");
		}
		log.print('d',"Players in DB:"+ counterOfPlayers);
		return counterOfPlayers;
	}
}
