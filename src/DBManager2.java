import java.sql.ResultSet;
import java.sql.SQLException;


public class DBManager2 {
	private Utilities log; //log
	private DBManager dbm;
	public DBManager2()
	{
		dbm = new DBManager();
		log = new Utilities();
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
		
		log.print('d', "Tworzenie tabeli statków:");
		log.print('d', "CREATE TABLE IF NOT EXISTS ship (id integer,ovnerId integer, lvl int, current_x int, current_y int, busy boolean)");
		dbm.update( "CREATE TABLE IF NOT EXISTS ship (id integer,ovnerId integer, lvl int, current_x int, current_y int, busy boolean)");
	}
	
	
	// Statek
	public void addShip(VarShip ship)
	{
		dbm.update("insert into ship values("+ship.getId()+","+ship.getOvnerId()+",'"+ship.getLvl()+"','"+ship.getX()+"','"+ship.getY()+",'"+ship.getBusy()+"')");
		//przydałoby się sprawdzić, Id nie jest zajęte...
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
	public VarShip getShipBySector(int x, int y, int r)
	{
		int xMinusR = x-r;
		int xPlusR = x+r;
		int yMinusR = y-r;
		int yPlusR = y+r;
		log.print('d', "select * from ship WHERE current_x >"+xMinusR+" and current_x <"+xPlusR+" and current_y >"+yMinusR+" and current_y <"+yPlusR+" )");
		ResultSet rs = dbm.select2("select * from ship WHERE current_x >"+xMinusR+" and current_x <"+xPlusR+" and current_y >"+yMinusR+" and current_y <"+yPlusR+" )");
		try {
			return new VarShip(rs.getInt("id"),rs.getInt("ovnerId"),rs.getInt("lvl"), rs.getInt("current_x"), rs.getInt("current_y"), rs.getBoolean("busy"));
		} catch (SQLException e) 
		{
			System.out.println("ERROR - PlayersShip - getShip()");
		}
		return new VarShip(-1,-1,-1, -1, -1, true);
	}
	public void setShip(VarShip ship)
	{
		dbm.update("update ship set lvl = "+ship.getLvl()+",current_x = "+ship.getX()+",current_y = "+ship.getY()+",busy = "+ship.getBusy()+" where id="+ship.getId()+ ";");
	}
	
	// Gracz
	public String addPlayer(VarPlayer player)
	{
		boolean correct = false;
		log.print('f', "check name: "+player.getName());	// tu widzi player
		player.initTest();									// tu już nie
		correct = player.CheckName(player.getName());
		log.print('f', "ok");

		if(correct==true)
		{
			if(getPlayerByName(player.getName())==null)
			{
				int counter =printAllPlayers();
				log.print('d',"insert into players (id, username, password, mail, points, banned) values("+counter+",'"+player.getName()+"','"+player.getPassword()+"','"+player.getMail()+"',0, 'false')");	
				dbm.update("insert into players (id, username, password, mail, points, banned) values("+counter+",'"+player.getName()+"','"+player.getPassword()+"','"+player.getMail()+"',0, 'false')");	
				player.setID(counter);
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
		if( getPlayerByName(login).getPassword() == psswd )	
		{
			return true;
		}
		else return false;
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