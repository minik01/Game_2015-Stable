class Galaxy
{
	final static int MAXCUBES = 100;
	public int getMaxCubes()
	{
		return MAXCUBES;
	}
	private Cubes[] cubes = new Cubes[MAXCUBES];
	public Cubes getCube(int id)
	{
		return cubes[id];
	}
	public Galaxy()
	{
		for(int i=0; i< MAXCUBES;i++)
			cubes[i] = new Cubes(i);
		System.out.println("Galaktyka gotowa!");
	}
	public void hello()//Galaxy
	{
		for(int i=0; i< MAXCUBES;i++)
		{
			System.out.println("sześcian: "+i);
			cubes[i].hello();
		}
	}
}
class Cubes
{
	private int MAXSS;
	public int getMaxSS()
	{
		return MAXSS;
	}
	public SolarSystems getSSystems(int id)
	{
		return SSystems[id];
	}
	private SolarSystems[] SSystems;
	public Cubes(int id)
	{
		MAXSS = (-id/4)+2000/(id+10)+100;
		SSystems = new SolarSystems[MAXSS];
		for(int i=0; i< MAXSS;i++)
			SSystems[i] = new SolarSystems(i,id);
	}
	public void hello()//Cubes
	{
		for(int i=0; i< MAXSS;i++)
		{
			System.out.println("układ słoneczny: "+i);
			SSystems[i].hello();
		}
	}

}
class SolarSystems
{
	private int MAXPlanets;
	public int getMaxPlanets()
	{
		return MAXPlanets;
	}
	private Planets[] planets;
	public SolarSystems(int SolarSystemName,int CubeName)
	{
		MAXPlanets = SolarSystemName%4 + 6;
		planets = new Planets[MAXPlanets];
		for(int i=0; i< MAXPlanets;i++)
			planets[i] = new Planets(i,SolarSystemName,CubeName);
	}
	public void hello()//SolarSystem
	{
		for(int i=0; i< MAXPlanets;i++)
			planets[i].hello();
	}
}
class Planets
{
	private String name;
	private int r1;
	public Planets(int id,int SolarSystemName,int CubeName)
	{
		name = "nn"+CubeName+"-"+SolarSystemName+"-"+id;
		r1 = (int) Math.abs( id*(44+SolarSystemName+CubeName-SolarSystemName*id%(CubeName+4) ) );
	}
	public void hello()//Planets
	{
		System.out.println(name + " r="+r1);
	}
}
