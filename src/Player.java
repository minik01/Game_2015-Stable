import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Player	
{
	Position pos,dest;
	private int counterOfPlayers;
	DBManager dbm;
	public Player(DBManager DBM)
	{
		dbm = DBM;
		printAll();
		
	}
	/*	
	public int newPlayer(String name, String password)
	{
		Utilities util = new Utilities();
		int err = util.CheckName(name);
		if(err==0)
		{
			if(findByName(name)==-1)
			{
				dbm.update("insert into players values("+counterOfPlayers+",'"+name+"','"+password+"',0,0,0,0,0,0)");		
				counterOfPlayers++;
				return 0;
			}
			else return 32;
		} 
		else
		{
			
			return err;
		}
	}*/
	// Name parser
	public boolean CheckName(String name) {
		String regex = "^[A-Za-z0-9_-]{3,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		boolean err = matcher.matches();
		//System.out.println("regex:" + regex + "\nerr:" + err + "\nname:" + name);
		return err;
	}
	public String newPlayer(String name, String password)
	{
		boolean err = CheckName(name);
		
		if(err==true)
		{
			if(findByName(name)==-1)
			{
				dbm.update("insert into players values("+counterOfPlayers+",'"+name+"','"+password+"',0,0,0,0,0,0)");		
				counterOfPlayers++;
				return "Your account has been created successfully";
			}
			else return "This Username is already taken";
		} 
		else
		{
			return "You used illegal characters";
		}
	}
	/*public void clear()
	{
	 	dbm.update("drop table if exists players");
		initTable();
	}*/
	public void initTable()
	{	
		counterOfPlayers = 0;
		dbm.update( "CREATE TABLE IF NOT EXISTS players (id integer, name string, password string, posPlanet int, posSS int, posCube int, destPlanet int, destSS int, destCube int)");
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
	public void setPos(int Id,int Cube,int SS,int Planet)
	{
		dbm.update("UPDATE players SET posCube = '"+Cube+"', posSS = '"+SS+"', posPlanet = '"+Planet+"' WHERE id =  '"+Id+"' LIMIT 1;");
	}
	public void setDest(int Id,int Cube,int SS,int Planet)
	{
		dbm.update("UPDATE players SET destCube = '"+Cube+"', destSS = '"+SS+"', destPlanet = '"+Planet+"' WHERE id =  '"+Id+"' LIMIT 1;");
	}
	public Position getPos(int Id)
	{
		pos = dbm.getPos(Id);
		return pos;
	}
	
	public void printAll()
	{
		System.out.println("====================================Gracze================================");
		counterOfPlayers = 0;
		try{
			ResultSet rs = dbm.select2("select * from players");
			while(rs.next())
			{
				// read the result set
				System.out.println("+");
				System.out.println("+ id = " + rs.getInt("id"));
				System.out.println("+ name = " + rs.getString("name"));
				System.out.println("+ password = " + rs.getString("password"));
				counterOfPlayers++;
			}
		System.out.println("==========================================================================");
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in Player.printAll()");
		}
		System.out.println("Players in DB:"+ counterOfPlayers);
    }
}