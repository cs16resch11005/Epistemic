import java.util.HashMap; //sastry:9441491996; students.accounts@iith.ac.in

public class EpistemicRelatedStates
{
	protected static Integer [] posStates;
    HashMap<Integer, Integer[]> hmGStates; // = 
	protected static int totalStates;
	protected static int currentState;
		
	EpistemicRelatedStates()
	{
		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//posStates = new int[ConfigParameters.k_Value*ConfigParameters.num_People][ConfigParameters.num_People];
		posStates    = new Integer[ConfigParameters.num_People];
		hmGStates    = new HashMap<Integer, Integer[]>();
		totalStates  = 0;
		currentState = 0;
	}	
	
	public int findDynamicPossibleAgents()
	{
		int count=0;		
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			Agent agent = Simulation.population.get(i);			
			if(agent.currState != 2)
				count = count + 1;		
		}		
		return count;
	}
	
	public boolean checkExistence(Integer[] gstate)
	{
		for(int i=0; i<totalStates; i++)
		{
			boolean isExist = true;
			
			for(int j=0; j<ConfigParameters.num_People; j++)
			{
				Integer[] cstate = hmGStates.get(j);
				
				if(gstate[j] != cstate[j])
				{
					isExist = false; 
					break;					
				}
			}
			
			if(isExist)
				return true;
		}
		
		return false;		
	}	
	
	
	public boolean checkFormulaOnAllGlobalState(int agentId, Integer [] gstate)
	{
		QueryEvaluation qe = new QueryEvaluation();
		boolean isSatisfied = qe.checkformulaOnGlobalState(gstate, Simulation.threshold, 1);
		currentState = currentState + 1;
		return isSatisfied;
	}
		
	public boolean generateEpistemicPossibleStates(int agentId, Integer [] gstate)
	{
		int posAgents = findDynamicPossibleAgents();
		//int sampleSize = (int)(Math.pow(2, posAgents));
		int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//System.out.println("\nGenerated Epistemic Possible States at Time : " + Simulation.currentTime);
		QueryEvaluation qe = new QueryEvaluation();
			
		//if(Simulation.currentTime < 6)
		//	return false;
			
		for(int j=0; j<sampleSize; j++)		
		{
			//System.out.print("\nState " + j + " :");
			for(int i=0; i<ConfigParameters.num_People; i++)
			{
				Agent agent = Simulation.population.get(i);
				
				if(i >= agentId)
				{
					posStates[i] = trasitionFunction(agent);
				}
				else
				{
					posStates[i] = agent.currState; 
				}				
				//System.out.print(" " + posStates[i]);
			}			
			//if(!checkExistence(posStates))
			//System.out.println();
			
			if(!hmGStates.containsValue(posStates))
			{
				hmGStates.put(totalStates, posStates);
				totalStates = totalStates + 1;
			}			
		}		
		//System.out.println();	
		//Simulation.isQuerySatisfied = true;	
		
		if(currentState < totalStates)
		{	
			boolean isValid = checkFormulaOnAllGlobalState(agentId, hmGStates.get(currentState));
			
			if(isValid == false)
			   return false;
			else
				generateEpistemicPossibleStates(agentId, hmGStates.get(currentState));
		}
		
		return true;
	}
	
	public int trasitionFunction(Agent agent)
	{
		int currState = agent.currState;		
		double randum = Math.random();
		
		if(currState == 0)
		{
			if(randum < 0.1)
				currState = 1;
			else
				currState = 0;
		}
		else
		{
			if(currState == 1)
			{
				if(randum < 0.1)
					currState = 2;
				else
					currState = 1;
			}
			else
			{
				currState = 2;
			}			
		}		
		return currState;
	}
}
