import java.util.ArrayList;

public class ParserAjax 
{
	private Utilities log; //obowiązuje w tej klasie log 'r' przy odebranym i 's' przy wysyłanym 
	ArrayList<String> response;
	String request;
	DBManager2 dbm2;
	int count;
	public ParserAjax(DBManager2 dbm2,String request)
	{
		count = 0;
		log = new Utilities();
		request = request.substring(5,request.length());
		log.print('r', "ajax request: "+request);
		this.request = request;
		this.dbm2 = dbm2;
		setResponse();
	}

	private void setResponse()
	{
		int id = 0; //id też trzeba będzie wysyłać w zapytaniu.
		//ArrayList<String> lista = 
		//tu dodaj zapytanie dp bazy danych
		VarShip ship = dbm2.getShipById(id);
		dbm2.getShipsBySector(ship.getX(), ship.getY(), 150);
		
		response = new ArrayList<String>();
		response.add("<baza>");				//dodawanie statyczne
		response.add("  <ship>");
		response.add("    <id>1</id>");
		response.add("    <x>123</x>");
		response.add("    <y>122</y>");
		response.add("    <busy>false</busy>");
		response.add("  </ship>");
		response.add("</baza>");
		// możesz zrobić metodę w VarShip toXML zwracającą ArrayList i tylko przepisywać kolejne linijki
		// tak samo z VarPlayer
		
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
