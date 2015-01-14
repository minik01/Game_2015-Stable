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
	Timers3 clock= new Timers3(0);
	Timers2 timer = new Timers2(dbm2,clock);
	//Timers3 timer2 = new Timers3(1);
	//Runnable timer2 = new Timers3(1);
    Thread threads = new Thread(timer);
    threads.start();
    System.out.println("już");
    //timer2.
    //timer.startNewTurn();
    
    while(true)
    {
	    for(int i=0; i<MAXUSERS; i++) 
		    {//new 
				pserver.server(i);
				runners2.add(new SerwerHTTP(i,dbm2, clock));
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