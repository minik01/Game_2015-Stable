
public class parserHTTP 
{
	private Utilities log; //log
	private DBManager2 dbm2;
	private String special[] = null;
	private String link[] = null;
	private String name = null;
	private String password = null;
	private String mail= null;
	
	public parserHTTP(DBManager2 dbm2, String input, String [] special)
	{
		log = new Utilities();
		this.special = special;
		this.dbm2=dbm2;
	}
	public String getSpecjal(int i)
	{
		return special[i];
	};
	public String parser(String input)
	{
		int i;
		for(i=0;i<input.length();i++)
		{
			if(input.startsWith("?imie=", i)) //szukanie w odpowiedzi pola 'imie'
			{
				int NameStart = i+6;
				i=i+6;
				for(;i<input.length();i++)
				{
					if(input.charAt(i) == '&')
					{
						name = input.substring(NameStart,i);
						log.print('f',"imie="+name+" ([0]!)");
						break;
					} 
				}
			}

			if(input.startsWith("&nic=",i)) //szukanie w odpowiedzi pola 'nic'
			{
				int NameStart = i+5;
				i=i+5;
				for(;i<input.length();i++)
				{
					if(input.charAt(i) == '&' || input.charAt(i) == ' ')
					{
						password = input.substring(NameStart,i);	
						log.print('f',"psswd="+password+" ([1]!)");
						break;
					}
				}
			}
			if(input.startsWith("&mail=",i)) //szukanie w odpowiedzi pola 'mail'
			{
				int MailStart = i+6;
				i=i+6;
				for(;i<input.length();i++)
				{
					if(input.startsWith(" ", i))
					{
						mail = input.substring(MailStart,i);	
						log.print('f',"mail="+mail+" ([2]!)");
						break;
					}
				}
			}			
		}
		return getAddres(input);
	}
	private String getAddres(String input){
		link = input.split(" ");
		if(link[1].length()==1)
		{
			return "index.html";
		}
		else
		{
			String temp = link[1].substring(1);
			if(temp.startsWith("index"))
			{	
				special[0] = "";
				if(name!=null && password!=null && mail!= null) 					// Create new Player and get result (String)
				{
					VarPlayer player = new VarPlayer(name, password, mail);
					special[0] = dbm2.addPlayer(player);
					if(special[0]=="Your account has been created successfully") return "index.html";
					else return "rejestracja.html";
				}
				else return "index.html";
			}
			if(temp.startsWith("rejestracja"))
			{
				return "rejestracja.html";
			}
			if(temp.startsWith("TechTree"))
			{
				//special[2] = player.getTech().getTechTree().toHTML();
				return "TechTree.html";
			}
			if(temp.startsWith("game_map"))
			{
				return "game_map.html";
			}
			if(temp.startsWith("game"))
			{
				if(name!=null && password!=null) 				//logowanie się
				{
					if(dbm2.allowLogin(name,password))
					{
						return "game.html";
					}
				}
				log.print('f',"Nieudana próba logowania");
				special[0] = "Wrong Username or Password";
				return "index.html";
			}
			if(temp.startsWith("style.css"))
			{
				return "style.css";
			}
			if(temp.startsWith("sha512.js"))
			{
				return "sha512.js";
			}
			if(temp.startsWith("draw_map.js"))
			{
				return "draw_map.js";
			}
			if(temp.startsWith("draw_radar.js"))
			{
				return "draw_radar.js";
			}
			if(temp.startsWith("timer.js"))
			{
				return "timer.js";
			}
			if(temp.startsWith("favicon.ico"))
			{
				return "favicon.ico";
			}
			if(temp.startsWith("icon.png"))
			{
				return "icon.png";
			}
			if(temp.startsWith("bg.png"))
			{
				return "bg.png";
			}
			if(temp.startsWith("ajax")) //			+
			{
				return temp;
			}
			if(temp.startsWith("techImg"))
			{
				return temp;
			}

			/*  //tu wpisywać nazwy innych stron 
			if(link[1].equals("index.html"))
			{
				return link[1];
			}
			*/
			return "void.html";
		}
	}
}
