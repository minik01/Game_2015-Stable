import java.io.*;
import java.net.*;
import java.util.*;

public class SerwerHTTP implements Runnable
{
	private Utilities log;
	private String special[] = null;
	private int Id;
	//String special;
	WrapperPlayer player;
	public SerwerHTTP(WrapperPlayer player,int Id)                
	{    
		log = new Utilities();
		this.Id = Id;
		this.player = player;  
		special = new String[10];
	}
	@Override
	public void run()
	{
		try
		{      
			//for(int i=0;i<10;i++)special[i]=" ";
			special[0]=" ";
			ServerSocket serv=new ServerSocket(80); 
			while(true)
			{
				//przyjecie polaczenia 
				//System.out.println("Oczekiwanie na polaczenie...");
				Socket sock=serv.accept();
				log.print('c',"Connected - server no "+Id);               
	
				//strumienie danych                                               
				InputStream is=sock.getInputStream();                             
				OutputStream os=sock.getOutputStream();                           
				BufferedReader inp=new BufferedReader(new InputStreamReader(is)); 
				DataOutputStream outp=new DataOutputStream(os);                   
					                                                           
				//przyjecie zadania (request)                                     
				String request=inp.readLine();  
				log.print('c',"Request = "+request + "\n");                     
				//wyslanie odpowiedzi (response) 
				try
				{
					if(request.startsWith("GET"))                                     
					{
						parserHTTP HTTPP = new parserHTTP(player,request,special);
						String site = HTTPP.parser(request);
						Reader reader = new Reader(special);
						reader.openFile(site);
						
						//if(site.equals("game.html"))
							//special[1]=player.getPos(HTTPP.getId()).toString();
									
						//reader.setspecial(HTTPP.getspecial(0), 0);
						//response header    
						if(reader.bin())
						{
							reader.copy(outp);
						}
						else                                           
						{
							outp.writeBytes("HTTP/1.0 200 OK\r\n");                        
							outp.writeBytes("Content-Type: text/html\r\n");                
							outp.writeBytes("Content-Length: \r\n");                       
							outp.writeBytes("\r\n");
							//response body                                                
							while(true)
							{
								if(!reader.existNextLine())break;
								try 
								{
									outp.writeBytes(reader.nextLine()+"\n"); 
								}
								catch (IOException ex) 
								{
									System.out.println("Reading file error - SerwerHTTP - run()");
									break;
								}
								catch (NoSuchElementException e) 
								{
									System.out.println("Unexpected end of file - SerwerHTTP - run()");
									break;
								}
								
							} 
						}
					}
					else
					{
						outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");
					}  
				}
				catch(NullPointerException e)
				{
					
					System.out.println("Demand error - SerwerHTTP - run()");
					e.printStackTrace();
					break;
				}
			
				//TODO
				//if(client kończy połączenie) break;
				
				//zamykanie strumieni
				inp.close();
				outp.close();
				sock.close();//?
			}
			//sock.close();
			//serv.close();
		}
		catch(IOException e)
		{
			System.out.println("Server error ( may be connection) - SerwerHTTP - run()");
		}
	}
}
