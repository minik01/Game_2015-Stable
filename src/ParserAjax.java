import java.util.ArrayList;

public class ParserAjax 
{
	private Utilities log; //obowiazuje w tej klasie log 'r' przy odebranym i 's' przy wysylanym 
	public ArrayList<String> response;
	public String request;
	public DBManager2 dbm2;
	private Timers3 timer;
	int count;
	public ParserAjax(DBManager2 dbm2,String request,Timers3 timer2)
	{
		count = 0;
		log = new Utilities();
		request = request.substring(5,request.length());
		log.print('r', "ajax request: "+request);
		this.request = request;
		this.dbm2 = dbm2;
		this.timer = timer2;
		//setResponse();
		System.out.println("REQUEST AJAX: "+request);
		if(request.startsWith("time_request"))
			getTime();
		else if(request.startsWith("new_coords"))
			changeCoords();
	}
	private void getTime()
	{
		System.out.println("ACK for time!!!");
		response = new ArrayList<String>();
		long time = timer.getTime();
		String stime = Long.toString(time);
		System.out.println("startTime is : "+ time);
		
		response = new ArrayList<String>();
		response.add(stime);	
	}
	private void changeCoords() {
		String id_mark = "_ship_id=";
		String x_mark = "_pos_x=";
		String y_mark = "_pos_y=";
		
		int ship_id = response.indexOf(id_mark)+id_mark.length();
		int pos_x = response.indexOf(x_mark)+x_mark.length();
		int pos_y = response.indexOf(y_mark)+y_mark.length();
		
		//dbm2.getShipById()
		
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
