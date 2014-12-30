import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;

public class Timers extends TimerTask {
	
	private Utilities log;
	public XmlShipsManagement xmlman;
	public DBManager2 dbm2;
	
	public Timers(DBManager2 db){
		log = new Utilities();
		dbm2 = db;
	}
	
	public boolean ResetTurn(){
		
		try{
			
		
			Runnable reset = new Runnable() {
				
			    public void run() {
			    	
			    	System.out.println("Turn reset now!");
	                
	                
	                xmlman = new XmlShipsManagement("ships_data.xml","ship",dbm2);
			    	
			    	if(xmlman.copyToXML()) System.out.println("New turn !");
			    	
			    	else System.out.println("Error in transfer from db to xml!!!!!!!");
			    }
			};
	
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(reset, 0, 30, TimeUnit.SECONDS);
		
			return true; // true when calculations are over
		}catch(Exception ex) {
			System.out.println("Exception caught while reseting turn");
		    return false;

		}	
		
	}
	@Override
	 public void run() {
    	
    	
    }
	
}
