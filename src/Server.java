/*public class Server {
	private final static int MAXUSERS = 1;
	public static void main(String[] args) 
	{
	
	DBManager2 dbm2 = new DBManager2();
	dbm2.clear();

        Runnable[] runners = new Runnable[MAXUSERS];
        Thread[] threads = new Thread[MAXUSERS];
 
        Timers timer = new Timers(dbm2);
        timer.ResetTurn();
        
        for(int i=0; i<MAXUSERS; i++) 
        {
        	runners[i] = new SerwerHTTP(i, dbm2, timer);
        }
        
        for(int i=0; i<MAXUSERS; i++) {
            threads[i] = new Thread(runners[i]);
        }
 
        for(int i=0; i<MAXUSERS; i++) {
		threads[i].start();
        }
    }
}
*/
import java.util.ArrayList;

public class Server {
	private final static int MAXUSERS = 3;
	public static void main(String[] args) 
	{
	
	DBManager2 dbm2 = new DBManager2();
	//dbm2.clear();
	PreServer pserver = new PreServer();
	ArrayList<Runnable> runners2 =new ArrayList<Runnable>();
	ArrayList<Thread> threads2 =new ArrayList<Thread>();
	
	Timers timer = new Timers(dbm2);
    timer.ResetTurn();
    while(true)
    {
	    for(int i=0; i<MAXUSERS; i++) 
		    {//new 
				pserver.server(i);
				runners2.add(new SerwerHTTP(i,dbm2, timer));
				threads2.add(new Thread(runners2.get(i)));
				threads2.get(i).start();
			}
	    runners2.clear();
	    threads2.clear();
    }

	/* 
	   	Runnable[] runners = new Runnable[MAXUSERS];
        Thread[] threads = new Thread[MAXUSERS];
 
       // Timers timer = new Timers(dbm2);
        //timer.ResetTurn();
        
        for(int i=0; i<MAXUSERS; i++) 
        {
        	runners[i] = new SerwerHTTP(dbm2, i);
        }
        
        for(int i=0; i<MAXUSERS; i++) {
            threads[i] = new Thread(runners[i]);
        }
 
        for(int i=0; i<MAXUSERS; i++) {
		threads[i].start();
        }
    */
    }
}