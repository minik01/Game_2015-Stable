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
			return (special[0]+"<br>");
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
		//if (existNextLine())System.out.println("t");
		//else System.out.println("n");
		return parser(in.nextLine());
		//else return -1;
	}
}
