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
		log.print('a', request);
		this.request = request;
		this.dbm2 = dbm2;
		this.timer = timer2;
		//setResponse();
		if(request.startsWith("time_request"))
			getTime();
		else if(request.startsWith("new_coords"))
			changeCoords();
	}
	private void getTime()
	{

		response = new ArrayList<String>();
		long time = timer.getTime();
		String stime = Long.toString(time);
		//System.out.println("startTime is : "+ time);
		
		response = new ArrayList<String>();
		response.add(stime);	
	}
	private void changeCoords() {
		
		//patterns 
		String id_mark = "&ship_id=";
		String x_mark = "&pos_x=";
		String y_mark = "&pos_y=";
		String end_mark = "&over";
		
		// pattern lengths
		int ship_id_index_start = request.indexOf(id_mark)+id_mark.length();
		int pos_x_index_start = request.indexOf(x_mark)+x_mark.length();
		int pos_y_index_start = request.indexOf(y_mark)+y_mark.length();
		int last_index_start = request.indexOf(end_mark);
		//temp for new value
		int id_val,x_val,y_val;
		
		VarShip ship = new VarShip(-1,-1,-1,-1,-1,false);
		
		
		id_val = Integer.parseInt(request.substring(ship_id_index_start,request.indexOf(x_mark)));
		ship = dbm2.getShipById(id_val);
		System.out.println(id_val);
		
		x_val = Integer.parseInt(request.substring(pos_x_index_start,request.indexOf(y_mark)));
		ship.x = x_val;
		System.out.println(x_val);
		
		y_val = Integer.parseInt(request.substring(pos_y_index_start,last_index_start));
		ship.y = y_val;
		System.out.println(y_val);

			
		
		dbm2.setShip(ship);
		
		response = new ArrayList<String>();
		response.add("ok");	
		
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
		//log.print('r', "count = "+ count );
		count++;
		return count<response.size();
	}
}
