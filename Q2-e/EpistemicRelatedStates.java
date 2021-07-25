
public class EpistemicRelatedStates
{
	protected static int [] posStates;
	protected int [] copyStates = new int[ConfigParameters.num_People];
	
	EpistemicRelatedStates()
	{
		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//posStates = new int[ConfigParameters.k_Value*ConfigParameters.num_People][ConfigParameters.num_People];
		posStates = new int[ConfigParameters.num_People];
	}	
	
	public int findDynamicPossibleAgents(int agentId)
	{
		int count=0;	
		
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			Agent agent = Simulation.population.get(i);
			
			if(agent.currState != 2)
			{
				count = count + 1;
				copyStates[i] = -1;
			}	
			else
			{
				copyStates[i] = 2;
			}
			
			//System.out.print(" " + copyStates[i]);
		}		
		copyStates[agentId] = 2;
		//System.out.print(" count : " + count);
		return (count-1);
	}
	
	  public void decimalToBinary(int num)
	  {
	        // Creating and assigning binary array size
	        int[] binary = new int[ConfigParameters.num_People];
	        int id = 0;
	  
	        // Number should be positive
	        while (num > 0) {
	            binary[id++] = num % 2;
	            num = num / 2;
	            
	        }  
	        
	        for(int k=id; k<ConfigParameters.num_People; k++)
	        	binary[k] = 0;
	        
	        int j=0;
	       // System.out.print("\n Time : " + Simulation.currentTime + " ; ");
	        for(int i=0; i<ConfigParameters.num_People; i++)
			{
				Agent agent = Simulation.population.get(i);
				
				if(copyStates[i] != 2)
				{
					if(binary[j] == 1)
					{
						if(agent.currState == 0)
							posStates[i] = 1; 
						else
							posStates[i] = 2; 
						
					}	
					else
					{
						posStates[i] = agent.currState ;
					}
					j++;
				}
				else
				{
					posStates[i] = agent.currState ;
				}
				//System.out.print(" " + posStates[i]);
			}	
	    }
	
	public boolean generateEpistemicPossibleStates(int agentId, double threshold, int operartion)
	{
		int posAgents = findDynamicPossibleAgents(agentId);		
		int sampleSize = (int)(Math.pow(2, posAgents));
		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//System.out.println("\nGenerated Epistemic Possible States at Time : " + Simulation.currentTime);
		QueryEvaluation qe = new QueryEvaluation();
		
		for(int j=0; j<sampleSize; j++)		
		{
			decimalToBinary(j);
			//System.out.print("\nState " + j + " :");
			boolean isSatisfied = qe.checkformulaOnGlobalState(posStates, threshold, operartion);			
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
