public class Server {
	private final static int MAXUSERS = 1;
	public static void main(String[] args) 
	{
	
	DBManager2 dbm2 = new DBManager2();
	dbm2.clear();

        Runnable[] runners = new Runnable[MAXUSERS];
        Thread[] threads = new Thread[MAXUSERS];
 
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
    }
}