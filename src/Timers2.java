
	public class Timers2 implements Runnable {
		 
//	    private int id;
	    private Utilities log;
	    private XmlShipsManagement xmlman;
		private DBManager2 dbm2;
		private VarTurn clock;
		public long startTime;
		public static int turnTime = 40;
		public static int calculateTime = 5;
	 
	    public Timers2(DBManager2 dbm2, VarTurn clock){
	    
	    	this.clock = clock;
			log = new Utilities();
			this.dbm2 = dbm2;
	    }
	 
	    @Override
	    public void run() {
	        while(true) {
	            try {
	            	for(int i = 0; i <= turnTime; i++)
	            	{
	            		Thread.sleep(1000);
	            		clock.setTime(turnTime-i);
	            		if(i == turnTime - calculateTime)
	            			log.print('t', "obliczam!");
	            	}
	                xmlman = new XmlShipsManagement("ships_data.xml","ship",dbm2);
			    	if(xmlman.copyToXML()) {
			    		log.print('t',"New turn !");
			    		
			    		
			    	}
			    	else System.out.println("Error in transfer from db to xml!!!!!!!");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
