import java.sql.ResultSet;
public class PlayersPosition 
{
	//private Utilities log; //log
	private DBManager dbm;
	public PlayersPosition(DBManager dbm)
	{
		//log = new Utilities();
		this.dbm = dbm;
		initTable();
	}
	
	public void newPlayer(int id, Position pos, Position dest)
	{
		dbm.update("insert into position values("+id+","+pos.getPosPlanet()+","+pos.getPosSS()+","+pos.getPosCube()+","+dest.getPosPlanet()+","+dest.getPosSS()+","+dest.getPosCube() + ")");		
		//counterOfPlayers++;
	}
	public void initTable()
	{	
		//counterOfPlayers = 0;
		dbm.update( "CREATE TABLE IF NOT EXISTS position (id integer, posPlanet int, posSS int, posCube int, destPlanet int, destSS int, destCube int)");
	}
	public Position getPos(int id)
	{
	   try
	    {
	   		ResultSet rs = dbm.select2("select * from position WHERE id="+id);
			return new Position(rs.getInt("posPlanet"),rs.getInt("posSS"),rs.getInt("posCube"));
	    }
	    catch(java.sql.SQLException e)
	    {
	    	return new Position(-1,-1,-1);
	    }
	}
	public void setPos(int Id,int Cube,int SS,int Planet)
	{
		dbm.update("UPDATE position SET posCube = '"+Cube+"', posSS = '"+SS+"', posPlanet = '"+Planet+"' WHERE id =  '"+Id+"' LIMIT 1;");
	}
	public void setDest(int Id,int Cube,int SS,int Planet)
	{
		dbm.update("UPDATE position SET destCube = '"+Cube+"', destSS = '"+SS+"', destPlanet = '"+Planet+"' WHERE id =  '"+Id+"' LIMIT 1;");
	}
}

