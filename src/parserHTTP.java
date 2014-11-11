import java.util.regex.Pattern;

public class parserHTTP 
{
	private Player player;
	private String specjal[] = null;
	private String link[] = null;
	private int Id;
	private String name = null;
	private String password = null;
	private String Planet = null;
	private String SS = null;
	private String Cube = null;
	
	public parserHTTP(Player player, String input, String [] specjal)
	{
		this.specjal = specjal ;
		//specjal = " ";
		this.player=player;
	}
	public int getId()
	{
		return Id;
	}
	public String getSpecjal(int i)
	{
		return specjal[i];
	};
	public String parser(String input)
	{
		int i;
		for(i=0;i<input.length();i++)
		{
			if(input.startsWith("?imie=", i)) //szukanie w odpowiedzi pola 'imie'
			{
				int NameStart = i+6;
				for(;i<input.length();i++)
				{
					if(input.startsWith("&", i))
					{
						name = input.substring(NameStart,i);
						//System.out.println("imie="+name+" ([0]!)");
						break;
					}
				}
			}

			if(input.startsWith("&nic=",i)) //szukanie w odpowiedzi pola 'nic'
			{
				int NameStart = i+5;
				for(;i<input.length();i++)
				{
					if(input.startsWith(" ", i))
					{
						password = input.substring(NameStart,i);			
						//System.out.println("psswd="+password+" ([1]!)");
						break;
					}
				}
			}
			if(input.startsWith("?P=", i)) //szukanie w odpowiedzi pola 'P'
			{
				int NameStart = i+4;
				for(;i<input.length();i++)
				{
					if(input.startsWith("&", i))
					{
						Planet = input.substring(NameStart,i);
						System.out.println("P="+Planet);
						break;
					}
				}
			}
			if(input.startsWith("&SS=", i)) //szukanie w odpowiedzi pola 'SS'
			{
				int NameStart = i+5;
				for(;i<input.length();i++)
				{
					if(input.startsWith("&", i))
					{
						SS = input.substring(NameStart,i);
						System.out.println("SS="+SS);
						break;
					}
				}
			}
			if(input.startsWith("&C=", i)) //szukanie w odpowiedzi pola 'C'
			{
				int NameStart = i+4;
				for(;i<input.length();i++)
				{
					if(input.startsWith("&", i))
					{
						Cube = input.substring(NameStart,i);
						System.out.println("Cube="+Cube);
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
			System.out.println(temp);
			if(temp.startsWith("index"))
			{
				
				specjal[0] = "";
				/*if(name!=null && password!=null) 				//towrzenie konta
					 STARE
				 	if(player.findByName(name)==-1)
					{
						//System.out.println("Tworzę postać");
						
						int err = player.newPlayer(name, password);
						if(err==0) specjal[0]= "Your account has been created successfully";
						else
						{
							Utilities util = new Utilities();
							specjal[0] = util.iErrorTosError(err);
							
						}
						
					}
					else
					{
						System.out.println("The same account already exist");
						specjal[0] = "Account already exist";
					}
					*/
					specjal[0] = player.newPlayer(name, password);
				
				return "index.html";
			}
			if(temp.startsWith("rejestracja"))
			{
				return "rejestracja.html";
			}
			if(temp.startsWith("game"))
			{
				if(name!=null && password!=null) 				//logowanie się
				{
					Id = player.find(name,password);
					//System.out.println("ID = " +Id);
					if(Id==-1)
					{
						specjal[0] = "Wrong Username or Password";
						return "index.html";
					}
				}
				return "game.html";
			}
			if(temp.startsWith("style.css"))
			{
				return "style.css";
			}
			if(temp.startsWith("sha512.js"))
			{
				return "sha512.js";
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
			
			if(temp.startsWith("setPos"))
			{
				if(Planet!=null && SS!=null && Cube!=null)
					return "setPos.html";
				else return "setPosFailed.html";
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
