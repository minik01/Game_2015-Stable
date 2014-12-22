import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager
{

	private Utilities log;
	private Statement statement;
	private Connection connection;
	private ResultSet rs;
	public void close()
	{
		try
		{
			if(connection != null)
			connection.close();
		}
		catch(SQLException e)
		{
			System.err.println(e);
		}
	}
	public void update(String querry)
	{
		try
		{
			statement.executeUpdate(querry);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
    }

    public int select(String querry)
    {
    	try
	    {
	      rs = statement.executeQuery(querry);
	      while(rs.next())
	      {
			if(log.logDB)
	    	  System.out.println("Found id:"+rs.getInt("id"));
	    	  return rs.getInt("id");
	      }
	    }
	    catch(SQLException e)
		{
			System.out.println("DB error - DBM - select()");
		}
    	return -1;
    }
    public boolean check(String tab,int id, String toCheck)
    {
    	try
	    {
	      rs = statement.executeQuery("select * from "+tab+"WHERE id="+id);
	      while(rs.next())
	      {
	    	  return rs.getBoolean(toCheck);
	      }
	    }
	    catch(SQLException e)
		{
			System.out.println("DB error - DBM - check()");
		}
    	return false;
    }
    public ResultSet select2(String querry)
    {
    	try
	    {
	      rs = statement.executeQuery(querry);
	      return rs;
	    }
	    catch(SQLException e)
	    {
			System.out.println("DB error - DBM - select2()");
	    }
    	return null;
    }
    public ResultSet select3(String tab, int id)
    {
    	try
	    {
	      rs = statement.executeQuery("select * from "+tab + "where id="+id);
	      return rs;
	    }
	    catch(SQLException e)
	    {
			System.out.println("DB error - DBM - select3()");
	    }
    	return null;
    }
    public DBManager()
    {
		log = new Utilities();
		try{
			Class.forName("org.sqlite.JDBC");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("DB error - cannot find org.sqlite.JDBC");
		}
		connection = null;
		try
		{
			connection = DriverManager.getConnection("jdbc:sqlite:players.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(5);  // set timeout to 5 sec
		 }
		 catch(SQLException e)
		 {
				System.out.println("DB error - constructor ");
		 }
    }
}
