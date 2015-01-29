import java.io.*;
import java.net.*;
import java.util.*;

public class PreServer {
	private Utilities log; //log
	private Constant cnt;
	public PreServer()
	{
		log = new Utilities();
		cnt = new Constant();
	}
	public void server (int id)
	{
		try
		{      
			ServerSocket serv=new ServerSocket(80); 
	
				//przyjecie polaczenia 
				log.print('c',"PreServer is waiting for clients...");
				Socket sock=serv.accept();	
				//strumienie danych                                               
				InputStream is=sock.getInputStream();                             
				OutputStream os=sock.getOutputStream();                           
				BufferedReader inp=new BufferedReader(new InputStreamReader(is)); 
				DataOutputStream outp=new DataOutputStream(os);                   
					                                                           
				//przyjecie zadania (request)                                     
				String request=inp.readLine();  
				//wyslanie odpowiedzi (response) 
				try
				{
					if(request != null)
					{
						if(request.startsWith("GET"))                                     
						{
								outp.writeBytes("HTTP/1.0 200 OK\r\n");                        
								outp.writeBytes("Content-Type: text/html\r\n");                
								outp.writeBytes("Content-Length: \r\n");                       
								outp.writeBytes("\r\n");
								//response body       
								outp.writeBytes("<html><head></head><body>");
								if(id!=-1)
								{
									outp.writeBytes("<script type='text/javascript'>");
									outp.writeBytes("window.location = 'http://" + cnt.addres +":"+(8000+id)+"' ;");
									outp.writeBytes("</script>");
								}
								else
								{
									outp.writeBytes("I'm sorry, but all of our servers are in use");
								}
								outp.writeBytes("</body></html>");
								
						}
						else
						{
							outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");
						}  
					}
			}
			catch(NullPointerException e)
			{
				System.out.println("Demand error - PreServer - Server()");
				e.printStackTrace();
			}				
			//zamykanie strumieni
			inp.close();
			outp.close();
			sock.close();
			serv.close();
		}
		catch(IOException e)
		{
			System.out.println("Server error ( may be connection) - PreServer - run()");
			e.printStackTrace();
		}
	}
}
