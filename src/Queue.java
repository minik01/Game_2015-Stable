import java.util.ArrayList;

public class Queue {
	private Utilities log; //log
	ArrayList<VarShip> queueShips;
	ArrayList<VarShip> shipList;
	DBManager2 dbm2;
	public Queue(DBManager2 dbm2)
	{
		this.dbm2 = dbm2;;
		log = new Utilities();
		queueShips = new ArrayList<VarShip>();
	}
	public void addShip(VarShip ship)
	{
		queueShips.add(ship);
	}
	public ArrayList<VarShip> getQueue()
	{
		return queueShips;
	}
	private void checkList()
	{
		int i,i2;
		shipList = new ArrayList<VarShip>();
		for(i=queueShips.size()-1; i>0 ;i--)
		{
			int tempId = queueShips.get(i).getId();
			boolean firstTime = true;
			for(i2=0; i2 < shipList.size();i2++)
			{
				if(shipList.get(i2).getId() == tempId)firstTime = false;
			}
			if(firstTime)shipList.add(queueShips.get(i));
		}
		}
	public void make()
	{
		checkList();
		for(int i=0; i < shipList.size();i++)
		{
			dbm2.addShip(shipList.get(i));
		}
	} 
}
