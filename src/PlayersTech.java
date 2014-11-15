public class PlayersTech 
{
	private DBManager dbm;
	private Utilities log;
	private TechTree tt;
	public PlayersTech(DBManager dbm)
	{
		tt = new TechTree();
		log = new Utilities();
		this.dbm = dbm;
		initTable();
	}
	public TechTree getTechTree()
	{
		return tt;
	}
	public void generateTechTree() //w pętli trzeba będzie przechodzić po wszystkich technologiach z TechTree, i dla każdego sprawdzać 
	{
		Tech[] ttTab = tt.toTab();
		int Id;
		for(int i=0;i<tt.getNumber();i++)
		{
			Id = ttTab[0].getId();
			if(dbm.check("PlayersTech", Id, "tech0") )ttTab[i].setObtained(); //Tu jest coś nie tak SPRAWDZIĆ
		}
	}
	public void initTable()
	{	
		//counterOfPlayers = 0;
		String temp =  "CREATE TABLE IF NOT EXISTS PlayersTech (Id int";
		for(int i = 0; i<tt.getNumber();i++)
		{
			temp += ", tech"+i+" boolean";
		}
		temp += ")";
		if(log.logTechTree)
			System.out.println("Creating table PlayersTech: "+temp);
		dbm.update(temp);
	}
	public void newPlayer(int id)
	{
		String temp =  "insert into PlayersTech (Id";
		for(int i = 0; i<tt.getNumber();i++)
		{
			temp += ", tech"+i;
		}
		temp += ") values("+id;
		for(int i = 0; i<tt.getNumber();i++)
		{
			if(i!=0)temp += ", 'false'";
			else temp += ", 'true'";
		}
		temp += ")";
		if(log.logTechTree || log.logDB)
			System.out.println("Adding players Tech: "+temp);
		dbm.update(temp);
	}
}
