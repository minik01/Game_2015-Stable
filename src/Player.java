import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Player	
{
	private Utilities log; //log
	Position pos,dest;
	private int counterOfPlayers;
	PlayersPosition position;
	PlayersTech tech;
	DBManager dbm;
	public Player()
	{
		log = new Utilities();
		dbm = new DBManager();
		initTable();
		position = new PlayersPosition(dbm);
		tech = new PlayersTech(dbm);
		printAll();
	}
/*
	public String newPlayer(String name, String password)
	{
		Utilities util = new Utilities();
		int err = util.CheckName(name);
		if(err==0)
		{
			if(findByName(name)==-1)
			{
				dbm.update("insert into players values("+counterOfPlayers+",'"+name+"','"+password+")");	
				position.setPos(counterOfPlayers, 1, 1, 2);
				position.setDest(counterOfPlayers, 1, 1, 2);				counterOfPlayers++;
				return "Your account has been created successfully";
			}
			else return "This Username is already taken";
		} 
		else
		{			
			return "<b>You used illegal characters</b>. <br>Username should consist of alphanumeric values. <br>It may be also _ and -.<br>Length should be minimum 3 and maximum 15.";
		}
	}
*/
	// Name parser
	public boolean CheckName(String name) {
		String regex = "^[A-Za-z0-9_-]{3,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		boolean err = matcher.matches();
		if(log.logForm)
			System.out.println("regex:" + regex + "\nerr:" + err + "\nname:" + name);
		return err;
	}
	
	public String newPlayer(String name, String password)
	{
		boolean correct = true;
		//correct = CheckName(name);
		if(correct==true)
		{
			if(findByName(name)==-1)
			{
				
				dbm.update("insert into players values("+counterOfPlayers+",'"+name+"','"+password+"')");	
				position.newPlayer(counterOfPlayers, new Position(1,1,2),new Position(2,3,2) );
				tech.newPlayer(counterOfPlayers);
				counterOfPlayers++;
				return "Your account has been created successfully";
			}
			else return "This Username is already taken";
		} 
		else
		{
			return "<b>You used illegal characters</b>. <br>Username should consist of alphanumeric values. <br>It may be also _ and -.<br>Length should be minimum 3 and maximum 15.";
		}
	}
	public PlayersTech getTech()
	{
		return tech;
	}
	public void clear()
	{
	 	dbm.update("drop table if exists players");
		initTable();
	}
	public void initTable()
	{	
		counterOfPlayers = 0;
		dbm.update( "CREATE TABLE IF NOT EXISTS players (id integer, name string, password string)");
	}
	public int findByName(String name)
	{
		int temp = dbm.select("select * from players WHERE name='"+name+"'");
		//System.out.println("numer:" + temp);
		return temp;
	}
	public int find(String name, String password)
	{
		return dbm.select("select * from players WHERE name='"+name+"'AND password='"+password+"'");
	}
	public Position getPos(int Id)
	{
		
		pos = position.getPos(Id);
		return pos;
	}
	
	public void printAll()
	{
		if(log.logDB)
			System.out.println("====================================Gracze================================");
		counterOfPlayers = 0;
		try{
			ResultSet rs = dbm.select2("select * from players");
			while(rs.next())
			{
				// read the result set
				if(log.logDB)
				{
					System.out.println("+");
					System.out.println("+ id = " + rs.getInt("id"));
					System.out.println("+ name = " + rs.getString("name"));
					System.out.println("+ password = " + rs.getString("password"));
				}
				counterOfPlayers++;
			}
			if(log.logDB)
				System.out.println("==========================================================================");
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in Player.printAll()");
		}
		if(log.logDB)
			System.out.println("Players in DB:"+ counterOfPlayers);
    }
}
