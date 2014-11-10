public class Server {
private final static int MAXUSERS = 128;
public static void main(String[] args) 
{
	//Galaxy galaxy= new Galaxy();
	DBManager DBM = new DBManager();
	Player player = new Player(DBM);
	//player.clear();
        Runnable[] runners = new Runnable[MAXUSERS];
        Thread[] threads = new Thread[MAXUSERS];
 
        for(int i=0; i<MAXUSERS; i++) 
        {
        	runners[i] = new SerwerHTTP(player, i);
        }
 
        for(int i=0; i<MAXUSERS; i++) {
            threads[i] = new Thread(runners[i]);
        }
 
        for(int i=0; i<MAXUSERS; i++) {
		threads[i].start();
        }
    }
}
