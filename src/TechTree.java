import java.sql.ResultSet;

public class TechTree //klasa powinna tylko odczytywać bazę danych - powinien być osobny program do generowania bazy danych.
{
	private int numberOfTech;
	private Utilities log; //log
	private DBManager dbm;
	public TechTree()
	{
		log = new Utilities();
		dbm = new DBManager();
		initTable();
		//newTech("tech1","opis",0,0,0);
		//newTech("tech2","opis2",0,0,0);
		//newTech("tech3","opis3",0,0,1);

		printAll();
	}
	public int getNumber()
	{
		return numberOfTech;
	}
	private void initTable()	// będzie używane przez osobny program, do generowania drzewa
	{
		dbm.update( "CREATE TABLE IF NOT EXISTS techTree (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name string, descripion string, Req0 int, Req1 int, Req2 int)");
	}
	public void newTech(String name, String descripion,int Req0,int Req1,int Req2)
	{
		dbm.update( "insert into techTree (name,descripion,Req0,Req1,Req2) values('" +name+ "','" +descripion+ "',"+Req0+","+Req1+","+Req2+")" );		
		//counterOfPlayers++;
	}
	public Tech select(int Id)
	{
		try{
			ResultSet rs = dbm.select2("select * from techTree WHERE id="+Id);
			return new Tech(Id,rs.getString("name"),rs.getString("descripion"),rs.getInt("Req0"),rs.getInt("Req1"),rs.getInt("Req2"));
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in TechTree.selcet()");
			return new Tech(-1,"error","error",-1,-1,-1);
		}	
	}
	public String toHTML()
	{
		String temp = "";
		try{
			ResultSet rs = dbm.select2("select * from techTree");
			while(rs.next())
			{
					// read the result set
					temp += "<img src='techImg" + rs.getInt("id")+".png' alt='"+rs.getString("descripion")+"'> "+ rs.getString("name");
					//+= "+ name = " + rs.getString("name");				}

				numberOfTech++;
			}
		}
		catch(java.sql.SQLException e)
		{
			temp = "error - DBM TechTree - toHTML()";
			System.out.println("java.sql.SQLException in TechTree.toHTML()");
		}
		return temp;
	}
	public Tech[] toTab()
	{
		Tech[] temp = null;
		try{
			ResultSet rs = dbm.select2("select * from techTree");
			while(rs.next())
			{
				temp = new Tech[numberOfTech];
				for(int i= 0; i<numberOfTech;i++)temp[i] = new Tech(i,rs.getString("name"),rs.getString("descripion"),rs.getInt("req0"),rs.getInt("req1"),rs.getInt("req2"));
			}
		}
		catch(java.sql.SQLException e)
		{
			temp = null;
			System.out.println("java.sql.SQLException in TechTree.toTab()");
		}
		return temp;
	}
	public void printAll()
	{
		numberOfTech = 0;
		if(log.logDB)
			System.out.println("===========================Drzewo Technologiczne==========================");
		try{
			ResultSet rs = dbm.select2("select * from techTree");
			while(rs.next())
			{
				if(log.logDB)
				{
					// read the result set
					System.out.println("+");
					System.out.println("+ id = " + rs.getInt("id"));
					System.out.println("+ name = " + rs.getString("name"));
					System.out.println("+ descripion = " + rs.getString("descripion"));
				}
				numberOfTech++;
			}
			if(log.logDB)
				System.out.println("==========================================================================");
			if(log.logDB || log.logTechTree)
				System.out.println("Tech in base: "+numberOfTech);
		}
		catch(java.sql.SQLException e)
		{
			System.out.println("java.sql.SQLException in TechTree.printAll()");
		}
	}
}