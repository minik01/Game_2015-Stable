import java.util.Timer;


public class Timers extends TimerTask {{
	
	private Utilities log;
	public XmlShipsManagement xmlman;
	
	public Timers(){
		log = new Utilities();
	}
	
	public boolean ResetTurn(DBManager2 dbm2){
		
		try{
			
		}
			Runnable reset = new Runnable() {
			    public void run() {
			    	
			    	log.print("Turn reset now!")
	                
	                // TODO tutaj trzeba pobrac tabele ships z bazy danych i wyciagnac wartosci
	                
			    	
			    	
	                // TODO tutaj trzeba zapisac to do pliku xml 
	                xmlman = new XmlShipsManagement("ships_data.xml","ship");
			    }
			};
	
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(reset, 0, 30, TimeUnit.SECONDS);
		
			return true; // true when calculations are over
		}catch(ExecutionException ex) {
		    log.print("Exception caught while reseting turn");
		    return false;

		}	
		
	}
	
	
}
