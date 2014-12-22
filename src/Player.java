import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Player	
{
	private static String templateForQuerry = "players (id, username, password, mail, points, banned)";
	private Utilities log; //log
	Position pos,dest;
	private int counterOfPlayers;
	//PlayersPosition position;
	PlayersTech tech;
	PlayersShip ship;
	DBManager dbm;
	public Player()
	{
		log = new Utilities();
		dbm = new DBManager();
		initTable();
		//position = new PlayersPosition(dbm);
		ship = new PlayersShip(dbm);
		tech = new PlayersTech(dbm);
		printAll();
	}
	// Name parser
	public boolean CheckName(String username) {
		String regex = "^[A-Za-z0-9_-]{3,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		boolean err = matcher.matches();
		log.print('f',"regex:" + regex + "\nerr:" + err + "\nusername:" + username);
		return err;
	}
	
	public String newPlayer(String username, String password, String mail)
	{
		boolean correct = true;
		//correct = CheckName(username);
		if(correct==true)
		{
			if(findByName(username)==-1)
			{
				log.print('d',"insert into "+ templateForQuerry +" values("+counterOfPlayers+",'"+username+"','"+password+"','"+mail+"',0, 'false')");
				dbm.update("insert into "+ templateForQuerry +" values("+counterOfPlayers+",'"+username+"','"+password+"','"+mail+"',0, 'false')");	
				//position.newPlayer(counterOfPlayers, new Position(1,1,2),new Position(2,3,2) );
				tech.newPlayer(counterOfPlayers);
				counterOfPlayers++;
				if(log.logForm || log.logDB)
				{
					System.out.println("utworzono konto");
					printAll();
				}
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
		dbm.update( "CREATE TABLE IF NOT EXISTS players (id integer, username string, password string, mail string, points int, banned boolean)");
	}
	public int findByName(String username)
	{
		int temp = dbm.select("select * from players WHERE username='"+username+"'");
		//System.out.println("numer:" + temp);
		return temp;
	}
	public int find(String username, String password)
	{
		return dbm.select("select * from players WHERE username='"+username+"'AND password='"+password+"'");
	}
/*	public Position getPos(int Id)
	{
		pos = position.getPos(Id);
		return pos;
	}
	*/
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
					System.out.println("+ username = " + rs.getString("username"));
					System.out.println("+ password = " + rs.getString("password"));
					System.out.println("+ e-mail = " + rs.getString("mail"));
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
