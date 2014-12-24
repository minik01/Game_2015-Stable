public class Server {
	private final static int MAXUSERS = 1;
	public static void main(String[] args) 
	{
	//Galaxy galaxy= new Galaxy();
	//DBManager DBM = new DBManager();
	//TechTree tt = new TechTree();
	//tt.newTech("tech2", "inny opis", 0, 0, 1);
	//tt.printAll();
	
	WrapperPlayer wPlayer = new WrapperPlayer();
	wPlayer.clear();
	//Player player = new Player();
	//player.clear();
        Runnable[] runners = new Runnable[MAXUSERS];
        Thread[] threads = new Thread[MAXUSERS];
 
        for(int i=0; i<MAXUSERS; i++) 
        {
        	runners[i] = new SerwerHTTP(wPlayer, i);
        }
 
        for(int i=0; i<MAXUSERS; i++) {
            threads[i] = new Thread(runners[i]);
        }
 
        for(int i=0; i<MAXUSERS; i++) {
		threads[i].start();
        }
    }
}