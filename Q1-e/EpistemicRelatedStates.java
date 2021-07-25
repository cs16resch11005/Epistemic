
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

	public void generateEpistemicPossibleStates(int agentId)
	{
		int posAgents = findDynamicPossibleAgents();		
		int sampleSize = (int) Math.pow(2, posAgents);

		//System.out.println("Current run : " + Simulation.currRunId  + " Time : " + Simulation.currentTime + " Pos Agents : " + posAgents + " Sample Size :" +sampleSize);

		//int sampleSize = ConfigParameters.k_Value*ConfigParameters.num_People;
		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//int sampleSize =  (int) Math.pow(2, ConfigParameters.num_People);//(int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//System.out.println("\nGenerated Epistemic Possible States at Time : " + Simulation.currentTime);
		QueryEvaluation qe = new QueryEvaluation();
		
		for(int j=0; j<sampleSize; j++)		
		{
			//System.out.print("\nState " + j + " :");

			for(int i=0; i<ConfigParameters.num_People; i++)
			{
				Agent agent = Simulation.population.get(i);
				
				if(i != agentId)
				{
					posStates[i] = trasitionFunction(agent);
				}
				else
				{
					posStates[i] = agent.currState; 
				}
				
			//	System.out.print(" " + posStates[i]);
			}
		
			boolean isSatisfied = qe.checkformulaOnGlobalState(posStates, Simulation.threshold, 1);
			
			if(isSatisfied == false)
			{
				Simulation.isQuerySatisfied = false;
				//System.out.println("Break came at state: " +j);
				return;
			} 			
		}		
		//System.out.println();	
		Simulation.isQuerySatisfied = true;	
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
