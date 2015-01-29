import java.util.ArrayList;

	public class Queue {
		 
		 private int time;
		    private ArrayList<VarShip> preQueue;
		    private ArrayList<VarShip> finalQueue;
		    private DBManager2 dbm2;
		    private Utilities log;
		    private Constant constant;

			private int[] activeServers;
			public Queue(int time, DBManager2 dbm2)
		    {
		    	this.dbm2 = dbm2;
		    	log = new Utilities();
		    	preQueue = new ArrayList<VarShip>();
		    	finalQueue = new ArrayList<VarShip>();
		        this.time = time;
		        constant = new Constant();
		        activeServers = new int[constant.maxusers];
		    	for(int i = 0; i < constant.maxusers; i++)
		    	{
		    		activeServers[i] = -1;
		    	}
		    }
		    public boolean amIAlive(int id)
		    {
		    	if(activeServers[id]<=0)return false;
		    	else return true;
		    }
		    public void iAmAlive(int id)
		    {
		    	activeServers[id] = 5;
		    	log.print('t', "Server "+id+ " is Alive!");
		    }
		    public int getFirstFreeServer()
		    {
		    	for(int i = 0; i < constant.maxusers; i++)
		    	{
		    		if(activeServers[i]<0){
		    			activeServers[i] = 2 ;
		    	    	log.print('t', "Server "+i+ " is free! ("+activeServers[i]+")");
		    			return i;
		    		}
		    		else
		    		{
		    			log.print('t', "Server "+i+ " is in use ("+activeServers[i]+")");
		    		}
		    	}
		    	return -1;
		    }
		    public void addShip(VarShip ship)
		    {
		    	preQueue.add(ship);
		    }
			public void endTurn()
		    {
			    int i,i2;
		    	for(i = 0; i < constant.maxusers; i++)
		    	{
		    		log.print('t', "server: "+i+" - "+activeServers[i]);
		    		if(activeServers[i] > -2)activeServers[i]--;
		    		if(activeServers[i]==0)
		    		{
		    			log.print('t', "DESTROY server "+i+"!");
		    		}
		    	}
			    finalQueue.clear();
			    for(i=preQueue.size()-1; i>0 ;i--)
			    {
				    int tempId = preQueue.get(i).getId();
				    boolean firstTime = true;
				    for(i2=0; i2 < finalQueue.size();i2++)
				    {
				    	if(finalQueue.get(i2).getId() == tempId)
				    		firstTime = false;
				    }
				    if(firstTime)finalQueue.add(preQueue.get(i));
			    }
			    
			    for(i=0; i < finalQueue.size();i++)
			    {
			    	dbm2.addShip(finalQueue.get(i));
			    }
			    log.print('t', "Kolejka zmian: " + finalQueue.size() + " pozycji");
		    }
		    public int getTime()
		    {
		    	return time;
		    }
		    public void setTime(int time)
		    {
		        this.time = time;
		    }
	}