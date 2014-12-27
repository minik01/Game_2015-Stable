public class Utilities {
	public Utilities(){}
	private boolean logConection = true;
	private boolean logReader = true;
	private boolean logForm = true;
	private boolean logDB = true;
	private boolean logTechTree = true;
	private boolean logSend = true;
	
	public int start_x = 100;
	public int start_y = 100;
	
	
	
	public void print(char type,String text) //pomyślałem, że mechanizm logów jest trochę słaby
	{
		switch(type)
		{
			case 'C':
				if(logConection)
					System.out.println("log:Conection> "+ text);break;
			case 'c':
				if(logConection)
					System.out.println("log:Conection> "+ text);break;
			
			case 'R':
				if(logReader)
					System.out.println("log:Reader> "+ text);break;
			case 'r':
				if(logReader)
					System.out.println("log:Reader> "+ text);break;
			
			case 'F':
				if(logForm)
					System.out.println("log:Form> "+ text);break;
			case 'f':
				if(logForm)
					System.out.println("log:Form> "+ text);break;
			
			case 'D':
				if(logDB)
					System.out.println("log:DB> "+ text);break;
			case 'd':
				if(logDB)
					System.out.println("log:DB> "+ text);break;
			
			case 'T':
				if(logTechTree)
					System.out.println("log:TechTree> "+ text);break;
			case 't':
				if(logTechTree)
					System.out.println("log:TechTree> "+ text);break;
					
			case 'S':
				if(logSend)
					System.out.println("log:Send> "+ text);break;
			case 's':
				if(logSend)
					System.out.println("log:Send> "+ text);break;

			default: System.out.println("log> "+ text);
		}
	}
	/*
	public String iErrorTosError(int iError)
	{
		switch(iError)
		{
			case 0:return "";
			case 1:return "Imię za krotkie!";
			case 2:return "W zadnym normalnym imieniu nie ma znaku srednika!";
			case 3:return "W zadnym normalnym imieniu nie ma wykrzyknika!";
			case 4:return "W zadnym normalnym imieniu nie ma znaku @!";
			case 5:return "W zadnym normalnym imieniu nie ma znaku #!";
			case 6:return "W zadnym normalnym imieniu nie ma znaku $!";
			case 7:return "W zadnym normalnym imieniu nie ma znaku %!";
			case 8:return "W zadnym normalnym imieniu nie ma znaku ^!";
			case 9:return "W zadnym normalnym imieniu nie ma znaku &!";
			case 10:return "W zadnym normalnym imieniu nie ma znaku *!";
			case 11:return "W zadnym normalnym imieniu nie ma znaku [!";
			case 12:return "W zadnym normalnym imieniu nie ma znaku ]!";
			case 13:return "W zadnym normalnym imieniu nie ma znaku -!";
			case 14:return "W zadnym normalnym imieniu nie ma znaku +!";
			case 15:return "W zadnym normalnym imieniu nie ma znaku =!";
			case 16:return "Uzyj spacji a nie _ !";
			case 17:return "Masz dwa imiona?!";
			case 18:return "W zadnym normalnym imieniu nie ma znaku \"!";
			case 19:return "W zadnym normalnym imieniu nie ma znaku |!";
			case 20:return "W zadnym normalnym imieniu nie ma znaku '!";
			case 21:return "W zadnym normalnym imieniu nie ma znaku <!";
			case 22:return "W zadnym normalnym imieniu nie ma przecinka!";
			case 23:return "W zadnym normalnym imieniu nie ma kropki!";
			case 24:return "W zadnym normalnym imieniu nie ma znaku >!";
			case 25:return "Masz dwa imiona?!";
			case 26:return "Pytasz się, czy odpowiadasz?!";
			case 27:return "W zadnym normalnym imieniu nie ma znaku `!";
			case 28:return "W zadnym normalnym imieniu nie ma tyldy!";
			case 29:return "W zadnym normalnym imieniu nie ma srednika!";
			case 30:return "Za duze odstępy, uzywaj pojedyńczej spacji";
			case 31:return "Pierwszy znak w imieniu musi być litera, a nie cyfra";
			case 32:return "Konto o podanej nazwie juz istnieje.";


			
			default:return "bład błędu :P";
		}
		
	}

	public int CheckName(String TempName)//jezeli złe imię to true, jezeli dobre to false
	  {
		if(TempName==null)return 1;
		if(TempName.isEmpty())return 1;
		if(TempName.length()<3)
		{
			return 1;
		}
		//mogłem zrobić tablicę zakazanych symboli i sprawdzać kazdy po kolei...
		if(TempName.indexOf(";")!=-1)
		{
			return 2;
		}
		if(TempName.indexOf("%21")!=-1)
		{
			return 3;
		}
		if(TempName.indexOf("%40")!=-1)
		{
			return 4;
		}
		if(TempName.indexOf("%23")!=-1)
		{
			return 5;
		}
		if(TempName.indexOf("%24")!=-1)
		{
			return 6;
		}
		if(TempName.indexOf("%25")!=-1)
		{
			return 7;
		}
		if(TempName.indexOf("%5E")!=-1)
		{
			return 8;
		}
		if(TempName.indexOf("%26")!=-1)
		{
			return 9;
		}
		if(TempName.indexOf("*")!=-1)
		{
			return 10;
		}
		if(TempName.indexOf("%5B")!=-1)
		{
			return 11;
		}
		if(TempName.indexOf("%5D")!=-1)
		{
			return 12;
		}
		if(TempName.indexOf("-")!=-1)
		{
			return 13;
		}
		if(TempName.indexOf("%2B")!=-1)
		{
			return 14;
		}
		if(TempName.indexOf("%3D")!=-1)
		{
			return 15;
		}
		if(TempName.indexOf("_")!=-1)
		{
			return 16;
		}
		if(TempName.indexOf("%5C")!=-1)
		{
			return 17;
		}
		if(TempName.indexOf("%22")!=-1)
		{
			return 18;
		}
		if(TempName.indexOf("%7C")!=-1)
		{
			return 19;
		}
		if(TempName.indexOf("%27")!=-1)
		{
			return 20;
		}
		if(TempName.indexOf("%3C")!=-1)
		{
			return 21;
		}
		if(TempName.indexOf("%2C")!=-1)
		{
			return 22;
		}
		if(TempName.indexOf(".")!=-1)
		{
			return 23;
		}
		if(TempName.indexOf("%3E")!=-1)
		{
			return 24;
		}
		if(TempName.indexOf("%2F")!=-1)
		{
			return 25;
		}
		if(TempName.indexOf("%3F")!=-1)
		{
			return 26;
		}
		if(TempName.indexOf("%60")!=-1)
		{
			return 27;
		}
		if(TempName.indexOf("%7E")!=-1)
		{
			return 28;
		}
		if(TempName.indexOf("%3B")!=-1)
		{
			return 29;
		}
		if(TempName.indexOf("++")!=-1)
		{
			return 30;
		}
		if(TempName.indexOf("0")==0 || TempName.indexOf("1")==0 || TempName.indexOf("2")==0 || TempName.indexOf("3")==0 || TempName.indexOf("4")==0
				|| TempName.indexOf("5")==0 || TempName.indexOf("6")==0 || TempName.indexOf("7")==0 || TempName.indexOf("8")==0
				|| TempName.indexOf("9")==0 || TempName.indexOf(" ")==0)
		{
			return 31;
		}

		return 0;
	  }*/
	
	
}
