import java.util.Vector;

public class Simulation 
{
	
	public static Vector<Agent> population        = new Vector<Agent>();	
	public static int currentTime                 = 0;	
	public static int currRunId					  = 0;
	public static double threshold = 0.6;
	public static boolean isQuerySatisfied;	
	public static boolean isQuerySatisfied1;	

	public static int numInf;	
	public static double infProb;
	
	QueryEvaluation qe; 
	EpistemicRelatedStates ers;
	
	Simulation(int runId)
	{
		currRunId       = runId;		
		currentTime     = 0;		
		isQuerySatisfied = false;
		isQuerySatisfied1 = false;
		population.clear();
		qe = new QueryEvaluation();		
		ers  = new EpistemicRelatedStates();
	}

	public void preConfiguration()
	{
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			Agent agent = new Agent(i);
			agent.states = new int[3];
			population.add(agent);
		}			
		initialStatesOfAgents();		
		Run();
	}

	public void Run() 
	{
		for(int i=0; i<ConfigParameters.sim_Time; i++)
		{
			currentTime = i;	
			//numInf = countNumberInfected();
			//infProb = (double)numInf/ConfigParameters.num_People; 
			//System.out.println("\nTime : " + i +" num inf: " + numInf +" inf prob: " + infProb );
			isQuerySatisfied  = ers.generateEpistemicPossibleStates(4);
			//isQuerySatisfied = qe.checkQuerySatisfied(threshold, 1);			
			//checkEvolutionQuerySatisfied();
			
			//isQuerySatisfied = checkGroupKnowledgeOperator();
			
			if(isQuerySatisfied)
				break;		
			
			updateStatesOfAgents();
		}				
	}
	
	public boolean checkGroupKnowledgeOperator()
	{
		boolean isvalid = false;
		
	 	for(int i=0; i<4; i++)
	 	{
	 		isvalid =  ers.generateEpistemicPossibleStates(i);
	 		if(isvalid == false)
	 			return false;
	 	}
	 	
		return true;
	}

	public void checkEvolutionQuerySatisfied()
	{
		
		int count = 0;
		isQuerySatisfied1 = false; 
		
		for(int j=0; j<ConfigParameters.num_People; j++)
		{			
			if(population.get(j).currState != 0)
			{	
				count = count + 1;
			}
		}
		
		//System.out.println("Count of Infected :" + count);
		
		if(count >= (threshold*ConfigParameters.num_People))
			isQuerySatisfied1 = true;
	}
	
	public void postConfiguration() 
	{
		EpistemicRelatedStates.posStates = null;		
	}
	
	public void generateEpistemicPossibleStates() {
		// TODO Auto-generated method stub
		
	}

	public int countNumberInfected()
	{		
		int count = 0;		
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			if(population.get(i).currState == 1)
				count = count+1 ;
		}
		return count;				
	}
	
	public void initialStatesOfAgents()
	{
		//System.out.print("\nInitial States of Agents: ");
		
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			double randum = Math.random();
			
			if(randum < 0.1)
			{
				population.get(i).currState = 1;
			}
			else
			{
				population.get(i).currState = 0;
			}
			//System.out.println("Agent Id : " + i + " state : " + population.get(i).currState);
			//System.out.print(" " + population.get(i).currState);
		}		
	}
	
	public void updateStatesOfAgents()
	{		
		//System.out.print("\n Updated States of Agents at Time : " + currentTime + "\n");
		
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			double randum = Math.random();
			Agent agent = population.get(i);
			
			if(agent.currState == 0)
			{
				if(randum < 0.1)
					agent.currState = 1;
				else
					agent.currState = 0;
			}
			else
			{
				if((agent.currState == 1))
				{	
					if(randum < 0.1)
						agent.currState = 2;
					else
						agent.currState = 1;
				}
				else
				{
					agent.currState = 2;
				}			
			}
			//System.out.println("Agent Id : " + i + " state : " + agent.currState);
		//	System.out.print(" " + agent.currState);
		}	
		/*System.out.print("\n");
		for(int i=0; i<ConfigParameters.num_People; i++)
			System.out.print(" " + population.get(i).currState);
		System.out.print("\n");*/
	}	
}
