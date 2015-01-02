import java.util.ArrayList;

public class ParserAjax 
{
	private Utilities log; //obowiazuje w tej klasie log 'r' przy odebranym i 's' przy wysylanym 
	public ArrayList<String> response;
	public String request;
	public DBManager2 dbm2;
	private Timers timer;
	int count;
	public ParserAjax(DBManager2 dbm2,String request,Timers timer)
	{
		count = 0;
		log = new Utilities();
		request = request.substring(5,request.length());
		log.print('r', "ajax request: "+request);
		this.request = request;
		this.dbm2 = dbm2;
		this.timer = timer;
		//setResponse();
		if(request.startsWith("ajax_time_request"))
			getTime();
	}
	private void getTime()
	{
		System.out.println("ACK for time!!!");
		response = new ArrayList<String>();
		int time = timer.getStartTime();
		String stime = Integer.toString(time);
		System.out.println("startTime is : "+ time);
		response.add(stime);	
	}
	private void setResponse()
	{
		int id = 0; //id tez trzeba bedzie wysylac w zapytaniu.
		//ArrayList<String> lista = 
		//tu dodaj zapytanie do bazy danych
		VarShip ship = dbm2.getShipById(id);
		//dbm2.getShipsBySector(ship.getX(), ship.getY(), 150);
		
		response = new ArrayList<String>();
		response.add("<baza>");				//dodawanie statyczne
		response.add("  <ship>");
		response.add("    <id>1</id>");
		response.add("    <x>123</x>");
		response.add("    <y>122</y>");
		response.add("    <busy>false</busy>");
		response.add("  </ship>");
		response.add("</baza>");
		
	} 
	public String nextLine()
	{
		return response.get(count);
	}
	public boolean existNextLine()
	{
		log.print('r', "count = "+ count );
		count++;
		return count<response.size();
	}
}
