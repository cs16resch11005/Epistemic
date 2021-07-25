import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;

public class testEpistemicSMC 
{

	/* calculate number of times simulation to be run using estimation method */
	public int calNumOfSimulations(double ci, double epsilon)
	{
		int num_sims = 0;
		epsilon = 2*epsilon*epsilon;
		ci = Math.log(2/ci);
		num_sims = (int)Math.ceil(ci/epsilon);
		return num_sims;
	}

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception
	{
		/*System.setOut(new PrintStream(new OutputStream() {
			public void write(int b){  NO-OP  }
		}));*/		 

		String outFile = System.getProperty("user.dir")+ "/../" +"Output/output.txt";
		String resFile = System.getProperty("user.dir")+ "/../" +"Output/results.txt";
		System.setOut(new PrintStream(new FileOutputStream(outFile)));		
		
		File fe = new File(resFile);    
		if(fe.exists())
			fe.delete();

		File configFile =  new File(System.getProperty("user.dir")+ "/../" +"Config/config.properties");
		ConfigParameters cp = new ConfigParameters(configFile);
		cp.read_configFile();	
		
		long startTime = System.currentTimeMillis();

		testEpistemicSMC th = new testEpistemicSMC();
		int numRuns   = th.calNumOfSimulations(ConfigParameters.val_Ci, ConfigParameters.val_Epsilon);
		int numSucess  = 0;
		int numFailure = 0;		
		int numEvSucess = 0;
		int numEvFailure = 0;	
		
		System.out.println("\nNum of Runs : " + numRuns);

		//int sampleSize = (int)(ConfigParameters.num_People * Math.log(ConfigParameters.num_People));
		//System.out.println("Sample Size :" + sampleSize);
		//System.exit(1);
		
		for(int j=0; j<numRuns; j++)
		{			
			Simulation sm = new Simulation(j);
			sm.preConfiguration();
			
			if(Simulation.isQuerySatisfied)
			{
				numSucess  = numSucess + 1;
			}
			else
			{
				numFailure = numFailure + 1;
			}
			
			if(Simulation.isQuerySatisfied1)
			{
				numEvSucess = numEvSucess +1;
			}
			else
			{
				numEvFailure = numEvFailure + 1;
			}
			sm.postConfiguration();
		}

		//System.exit(1);
		int totalRuns = numFailure+numSucess;

		FileWriter fw =  new FileWriter(resFile, true);
		fw.write("\nnum Sucess  : " + numSucess +"\tnum Failure : " + numFailure);
		fw.write("\nProbability of given query has been satisfied : " + (double)numSucess/totalRuns+"\n");
		fw.write("\nnum Sucess  : " + numEvSucess +"\tnum Failure : " + numEvFailure);
		fw.write("\nProbability of given query satisfied on Evolution Graph: " + (double)numEvSucess/totalRuns+"\n");
		
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);  //Total execution time in milli seconds
		System.out.println("\nExecution Time testGenericSMC:" + duration);
		fw.write("\nExecution Time testGenericSMC:" + duration);
		fw.close();
		System.err.println("Probability of given query has been satisfied : " + (double)numSucess/totalRuns+"\n");
		System.err.println("Execution Time testGenericSMC(in milli seconds) : " + duration);
	}
}
