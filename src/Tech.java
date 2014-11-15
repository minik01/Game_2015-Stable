public class Tech {
	private String name;
	private String descripiton;
	private int req0,req1,req2;
	private int id;
	private boolean obtained;
	public Tech(int id, String name,String descripiton,int req0,int req1,int req2)
	{
		this.name = name;
		this.descripiton = descripiton;
		this.req0 = req0;
		this.req1 = req1;
		this.req2 = req2;
	}
	public Tech(String name,String descripiton,int req0,int req1,int req2,boolean obtained)
	{
		this.name = name;
		this.descripiton = descripiton;
		this.req0 = req0;
		this.req1 = req1;
		this.req2 = req2;
		this.obtained = obtained;
	}
	public void setObtained()
	{
		obtained = true;
	}
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getDescripiton()
	{
		return descripiton;
	}
	public int getReq0()
	{
		return req0;
	}
	public int getReq1()
	{
		return req1;
	}
	public int getReq2()
	{
		return req2;
	}
}
