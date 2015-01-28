import java.util.ArrayList;

public class Server {
	private final static int MAXUSERS = 3;
	public static void main(String[] args) 
	{
	
	DBManager2 dbm2 = new DBManager2();

	PreServer pserver = new PreServer();
	ArrayList<Runnable> runners2 =new ArrayList<Runnable>();
	ArrayList<Thread> threads2 =new ArrayList<Thread>();
	
	dbm2.initTables();
	
	VarTurn turn= new VarTurn(0,dbm2);
	Timer timer = new Timer(dbm2,turn);
    Thread threads = new Thread(timer);
    threads.start();
	    while(true)
	    {
		    for(int i=0; i<MAXUSERS; i++) 
			{
				pserver.server(i);
				runners2.add(new SerwerHTTP(i,dbm2, turn));
				threads2.add(new Thread(runners2.get(i)));
				threads2.get(i).start();
			}
		    runners2.clear();
		    threads2.clear();
	    }
    }
}