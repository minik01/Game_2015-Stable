import java.util.ArrayList;

public class Server {
public static void main(String[] args) 
	{
	
		DBManager2 dbm2 = new DBManager2();
	
		PreServer pserver = new PreServer();
		ArrayList<Runnable> runners2 =new ArrayList<Runnable>();
		ArrayList<Thread> threads2 =new ArrayList<Thread>();
		Constant constant = new Constant();
		
		dbm2.initTables();
		Queue queue= new Queue(0,dbm2);
		Timer timer = new Timer(dbm2,queue);
	    Thread threads = new Thread(timer);
	    threads.start();
    
	    while(true)
	    {
	    	//threads.start();
	    	int i = queue.getFirstFreeServer();
			pserver.server(i);
			if(i!=-1)
			{
				runners2.add(new SerwerHTTP(i,dbm2, queue));
				threads2.add(new Thread(runners2.get(i)));
				threads2.get(i).start();
			}

	    }
    }
}