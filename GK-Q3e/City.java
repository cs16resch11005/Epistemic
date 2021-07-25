import java.util.Vector;

public class City 
{
	protected int id;		
	protected Vector<Integer> people;	
	
	City(int id)
	{
		this.id = id;
	}
	
	int self()
	{
		return id;
	}
	
	Vector<Integer> getPopulation()
	{
		return people;
	}	

	
}
