import java.io.*;
import java.net.*;
import java.util.*;

public class SerwerHTTP implements Runnable
{
	private Utilities log;
	private String special[] = null;
	private int Id;
	DBManager2 dbm2;
	private Timers3 clock;
	public SerwerHTTP(int Id, DBManager2 dbm2, Timers3 clock)                
	{    
		log = new Utilities();
		this.Id = Id;
		this.dbm2 = dbm2;  
		this.clock = clock;
		special = new String[10];
	}
	@Override
	public void run()
	{
		try
		{      
			special[0]=" ";
			ServerSocket serv=new ServerSocket(8000+Id); 
			while(true)
			{
				//przyjecie polaczenia 
				log.print('c',"Waiting for clients...");
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
					if(request != null)
					{
						if(request.startsWith("GET"))                                     
						{
							parserHTTP HTTPP = new parserHTTP(dbm2,request,special);
							
							String site = HTTPP.parser(request);

							Reader reader = new Reader(special);

							reader.openFile(site);

							//reader.setspecial(HTTPP.getspecial(0), 0);
							//response header    
							if(reader.bin())
							{
								reader.copy(outp);
							}
							else                                           
							{
								outp.writeBytes("HTTP/1.0 200 OK\r\n");                        
								if(reader.js())
								{
									outp.writeBytes("Content-Type: application/javascript\r\n"); 
								}
								else
								{
									outp.writeBytes("Content-Type: text/html\r\n");                
								}
								outp.writeBytes("Content-Length: \r\n");                       
								outp.writeBytes("\r\n");
								
								//response body                                                
								if(reader.isAjax())
								{
									ParserAjax pAjax = new ParserAjax(dbm2,site,clock );
									while(true)
									{
											outp.writeBytes(pAjax.nextLine()+"\n"); 
											if(!pAjax.existNextLine())
												break;
									}
								}
								else 
								{
									while(true)
									{
										if(!reader.existNextLine())
											break;
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
						}
						else
						{
							outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");
						}  
					}
				}
				catch(NullPointerException e)
				{
					
					System.out.println("Demand error - SerwerHTTP - run()");
					e.printStackTrace();
					break;
				}
				//zamykanie strumieni
				inp.close();
				outp.close();
				sock.close();
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
