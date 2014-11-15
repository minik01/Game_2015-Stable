class Position
{
	private int cube;
	private int SSystems;
	private int planet;
	
	public void setPosition(int cube,int SSystems,int planet)
	{
		this.cube = cube;
		this.SSystems = SSystems;
		this.planet = planet;		
//		onBus = false;
	}
	public void setPosition(Position pos)
	{
		cube= pos.getPosCube();
		SSystems = pos.getPosSS();
		planet = pos.getPosPlanet();
	}
	public String toString()
	{
		String temp;
		temp = "" +cube +"-"+SSystems + "-" + planet; 
		return temp;
	}
	public void setPosCube(int cube)
	{
		this.cube = cube;	
	}
	public int getPosCube()
	{
		return cube;	
	}
	public int getPosSS()
	{
		return SSystems;	
	}
	public void setPosSS(int SSystems)
	{
		this.SSystems = SSystems;
	}
	public int getPosPlanet()
	{
		return planet;	
	}
	public void setPosPlanet(int planet)
	{
		this.planet = planet;		
	}
	public boolean equals(Position p1)
	{
		if( (cube == p1.getPosCube())  &&  (SSystems == p1.getPosSS())  &&  (planet == p1.getPosPlanet()) ) return true;
		else return false;
	}
	public Position()
	{
	
	}
	public Position(int cube,int SSystems,int planet)
	{
		this.cube = cube;
		this.SSystems = SSystems;
		this.planet = planet;
	}
}
