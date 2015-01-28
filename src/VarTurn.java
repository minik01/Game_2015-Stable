	public class VarTurn {
		 
	    private int time;
	    private Queue list;
	 
	    public VarTurn(int time,DBManager2 dbm2)
	    {
	    	list = new Queue(dbm2);
	        this.time = time;
	        //list.test();
	    }
	    public Queue getQueue()
	    {
	    	return list;
	    }
	    public int getTime()
	    {
	    	return time;
	    }
	    public void setTime(int time)
	    {
	        this.time = time;
	        //System.out.println(time+" left");
	    }	    
	}

