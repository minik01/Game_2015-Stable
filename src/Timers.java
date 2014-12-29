import java.util.Timer;


public class Timers {
	
	private Utilities log; 
	public DBManager2 dbm2;
	public XmlShipsManagement xmlman;
	
	
	public boolean ResetTurn(){
		
		try {
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			                log.print("Turn reset now!")
			                
			                // TODO tutaj trzeba pobrac tabele ships z bazy danych i wyciagnac wartosci
			                
			                // TODO tutaj trzeba zapisac to do pliku xml 
			                xmlman = new XmlShipsManagement("ships_data.xml","ship");
			                
			                
			            }
			        }, 
			        30000 // [ms] = 30 s
			);
			return true; // true when calculations are over
		}catch(ExecutionException ex) {
		    log.print("Exception caught while reseting turn");

		}	
		
	}
	
	
}
