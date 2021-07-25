
public class QueryEvaluation
{
	public boolean checkQuerySatisfied(double threshold, int operation)
	{
		int sampleSize = ConfigParameters.k_Value*ConfigParameters.num_People;
		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));		
		//System.out.println("\nGenerated Epistemic Possible States at Time : " + Simulation.currentTime);
		
		for(int j=0; j<sampleSize; j++)		
		{
			boolean isSatisfied = checkformulaOnGlobalState(EpistemicRelatedStates.posStates, threshold, operation);
			
			if(isSatisfied == false)
			{
				return false;
			}
		}	
		
	    return true;
	}
	
	public boolean checkformulaOnGlobalState(int [] gState, double threshold, int operation)
	{
		int count = 0;
		boolean isSatsified = false; 	
		
		//System.out.println("\nCount of Infected :" + count);
		
		if(operation == 1)
		{
			for(int j=0; j<ConfigParameters.num_People; j++)
			{
				if(gState[j] != 0)
				{	
					count = count + 1;
				}
			}

			if(count >= (threshold*ConfigParameters.num_People))
			{	
				isSatsified = true;
				//System.out.println("\nCount of Infected greater than :" + count);
			}
		}
		else
		{
			for(int j=0; j<ConfigParameters.num_People; j++)
			{
				if(gState[j] == 2)
				{	
					count = count + 1;
				}
			}

			if(count <= (threshold*ConfigParameters.num_People))
			{
				isSatsified = true;
				//System.out.println("\nCount of Infected less than :" + count);
			}
		}
		
		return isSatsified;
	}
}
