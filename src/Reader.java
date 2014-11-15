import java.io.*;
import java.util.Scanner;
 
public class Reader{
	private File file; 
	private Scanner in;
	private boolean bin;
	private String[] special;

	public Reader(String[] special)
	{
		this.special = special;
  	}
	public void openFile(String Filename) throws FileNotFoundException
	{
		file = new File(Filename);
		in = new Scanner(file);
		bin=(Filename.endsWith(".ico") || Filename.endsWith(".png"));
	}
	public boolean bin()
	{
		return bin;
	}
	public void copy(DataOutputStream to) 
	{
		try{
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			//Tworzymy buforowany strumień do zapisu
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new DataOutputStream(to));
			int read = bufferedInputStream.read();
			while (read != -1)		// read() zwróci -1 jeśli plik się skończył
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

	private String parser(String input)
	{
		if(input.contains("<!0Tresc>"))
		{
			System.out.println("Found <!0Tresc> - will be changed to:" + special[0]);
			return (special[0]);
		}
		if(input.contains("<!1Tresc>"))
		{
			return (special[1]+"<br>");
		}
		else{
			//System.out.println("Zdanie "+input+" NIE zawiera <!1Tresc> , dodane zostanie ");
			return input;
		}
	}

	public boolean existNextLine()
	{
		return in.hasNext();
	}
	public String nextLine()
	{
		
		String special_line=in.nextLine(); // get line to check
		String string_out=special_line; // new line on output
	
		int ile=2; // how many markers do we have
		int i=0; // current marker
		while(i<ile)
		{
			if(special_line.contains("<!"+i+"Tresc>"))
			{
				System.out.println("Found <!"+i+"Tresc> - will be changed to:" + special[i]);
				int start=special_line.indexOf("<!"+i+"Tresc>");
				string_out = special_line.substring(0,start)+special[i]+special_line.substring(start);
			}
			i++;
		}
		return string_out;
		//else return -1;
	}
}
