
public class EpistemicRelatedStates
{
	protected static int [] posStates;
	
	EpistemicRelatedStates()
	{
		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//posStates = new int[ConfigParameters.k_Value*ConfigParameters.num_People][ConfigParameters.num_People];
		posStates = new int[ConfigParameters.num_People];
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
	
	public boolean generateEpistemicPossibleStates(int agentId)
	{
		//int sampleSize = ConfigParameters.k_Value*ConfigParameters.num_People;
		int posAgents = findDynamicPossibleAgents();
		//int sampleSize = (int)(posAgents * Math.log(posAgents));
		int sampleSize = (int)Math.pow(2, posAgents);
		//System.out.println("\nGenerated Epistemic Possible States at Time : " + Simulation.currentTime);
		QueryEvaluation qe = new QueryEvaluation();
		
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
		
			boolean isSatisfied = qe.checkformulaOnGlobalState(posStates, Simulation.threshold, 1);
			
			if(isSatisfied == false)
			{
				//Simulation.isQuerySatisfied = false;
				return false;
			} 			
		}		
		//System.out.println();	
		//Simulation.isQuerySatisfied = true;
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
