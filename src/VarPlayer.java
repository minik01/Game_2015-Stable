import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarPlayer {
	
	private Utilities log = new Utilities();
	private int id;
	private String name, psswd, mail;
	public VarPlayer(int id, String name, String psswd, String mail)
	{
		this.id = id;
		this.name = name;
		this.psswd = psswd;
		this.mail = mail;
	}
	public VarPlayer(String name, String psswd, String mail)			//gdy nie wiemy ile jest graczy na serwerze
	{
		this.name = name;
		this.psswd = psswd;
		this.mail = mail;
	}
	
	public boolean CheckName(String username) {
		log.print('f', "check name");
		String regex = "^[A-Za-z0-9_-]{3,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		boolean err = matcher.matches();
		log.print('f',"regex:" + regex + "\nerr:" + err + "\nusername:" + username);
		return err;
	}
	public String getName()
	{
		return name;
	}
	public String getPassword()
	{
		return psswd;
	}
	public String getMail()
	{
		return mail;
	}
	public int getId()
	{
		return id;
	}
	public void setID(int counter) {
		id = counter;
	}
}
