import java.io.*;
import java.util.Scanner;
 
public class Reader{
	private Utilities log; //log
	private File file; 
	private Scanner in;
	private boolean bin,ajax,js;
	private String[] special;
	//private char type;


	public Reader(String[] special)
	{
		log = new Utilities();
		this.special = special;
  	}
	public void openFile(String Filename) throws FileNotFoundException
	{
		if(ajax)
		{
			ajax = Filename.startsWith("ajax");
		}
		else
		{
			file = new File(Filename);
			in = new Scanner(file);
			log.print('r',"open file: "+Filename);
			js = Filename.endsWith(".js");
			bin = (Filename.endsWith(".ico") || Filename.endsWith(".png") || Filename.endsWith(".xml"));
		}
	}
	public boolean js()
	{
		if(js)		log.print('r',"js request");
		return js;
	}
	public boolean bin()
	{
		if(bin)		log.print('r',"binary request");
		return bin;
	}
	public boolean isAjax()
	{
		if(ajax)	log.print('r',"ajax request");
		return ajax;
	}
	public void copy(DataOutputStream to) 
	{
		try{
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			//Tworzymy buforowany strumien do zapisu
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new DataOutputStream(to));
			int read = bufferedInputStream.read();
			while (read != -1)		// read() zwroci -1 jezeli plik sie skonczyl
			{ 
				bufferedOutputStream.write(read);
				read = bufferedInputStream.read();
			}
			bufferedOutputStream.close();
			bufferedInputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("Reading file error - Reader - copy()");
		}
	}
	/*
	private String parser(String input)
	{
		if(input.contains("<!0Tresc>"))
		{
			log.print('r',"Found <!0Tresc> - will be changed to:" + special[0]);
			return (special[0]+"<br>");
		}
		if(input.contains("<!1Tresc>"))
		{
			log.print('r',"Found <!1Tresc> - will be changed to:" + special[1]);
			return (special[1]+"<br>");
		}
		if(input.contains("<!2Tresc>"))
		{
			log.print('r',"Found <!2Tresc> - will be changed to:" + special[2]);
			return (special[2]+"<br>");
		}
		else{
			return input;
		}
	}
	*/
	public boolean existNextLine()
	{
		return in.hasNext();
	}
	public String nextLine()
	{
		String special_line = in.nextLine(); // get line to check
		String string_out = special_line; // new line on output
	
		int markers=2; // how many markers do we have
		int i=0; // current marker
		while(i<markers)
		{
			if(special_line.contains("<!"+i+"Tresc>"))
			{
				log.print('r',"Found <!"+i+"Tresc> - will be changed to:" + special[i]);
				int start=special_line.indexOf("<!"+i+"Tresc>");
				string_out = special_line.substring(0,start)+special[i]+special_line.substring(start);
			}
			i++;
		}
		return string_out;
		//else return -1;
	}
}
