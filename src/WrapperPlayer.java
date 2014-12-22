import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WrapperPlayer {
	private Utilities log; //log
	private int counterOfPlayers;
	DBManager dbm;
	
	public String create(String username, String password, String mail)
	{
		boolean correct = true;
		//correct = CheckName(username);
		if(correct==true)
		{
			if(findByName(username)==-1)
			{
				log.print('d',"insert into players (id, username, password, mail, points, banned) values("+counterOfPlayers+",'"+username+"','"+password+"','"+mail+"',0, 'false')");	
				dbm.update("insert into players (id, username, password, mail, points, banned) values("+counterOfPlayers+",'"+username+"','"+password+"','"+mail+"',0, 'false')");	
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
	public void printAll()
	{
		log.print('d',"====================================Gracze================================");
		counterOfPlayers = 0;
		try{
			ResultSet rs = dbm.select2("select * from players");
			while(rs.next())
			{
				// read the result set
				if(log.logDB)
				{
					log.print('d',"+");
					log.print('d',"+ id = " + rs.getInt("id"));
					log.print('d',"+ username = " + rs.getString("username"));
					log.print('d',"+ password = " + rs.getString("password"));
					log.print('d',"+ e-mail = " + rs.getString("mail"));
				}
				counterOfPlayers++;
			}
			log.print('d',"==========================================================================");
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in Player.printAll()");
		}
		log.print('d',"Players in DB:"+ counterOfPlayers);
    }	
}
