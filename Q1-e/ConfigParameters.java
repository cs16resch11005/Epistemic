import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigParameters
{
	
	  private File config_File; 

	  public static int     num_Nodes;
	  public static int     sim_Time;
	  public static int     num_People;
	  public static int 	sample_Size;
	  public static int     k_Value;
	  public static double  val_Ci;	  
	  public static double  val_Epsilon;
	  
	  public static int     is_Random_Graphs;
	  public static int     compare_Strategy;
	  
	  public static String  path_network_folder;
	  public static String  path_position_folder;
	 
	  /* constructor */
	  ConfigParameters(File _configFile)
	  {
	    config_File = _configFile;		
	  }/* End of ESP() */

	  /* reading the configuration file and assign values to input variables */
	  public void read_configFile() throws Exception 
	  {	         
	     FileReader fr = null;		
		 try 
		 {
			             fr  = new FileReader(config_File);
			Properties props = new Properties();
			props.load(fr);

			num_Nodes 		              = Integer.parseInt(props.getProperty("num_nodes"));
			sim_Time 		              = Integer.parseInt(props.getProperty("time_units"));
			num_People			          = Integer.parseInt(props.getProperty("num_people"));
			sample_Size					  = Integer.parseInt(props.getProperty("sample_size"));
			k_Value 					  = Integer.parseInt(props.getProperty("k_value"));
			val_Ci					      = Double.parseDouble(props.getProperty("val_ci"));
			val_Epsilon					  = Double.parseDouble(props.getProperty("val_epsilon"));
			is_Random_Graphs			  = Integer.parseInt(props.getProperty("is_random_generated_graphs"));
			compare_Strategy			  = Integer.parseInt(props.getProperty("compare_strategy"));
			path_network_folder           = System.getProperty("user.dir")+ "/../" + props.getProperty("location_of_network_folder");
			path_position_folder          = System.getProperty("user.dir")+ "/../" + props.getProperty("location_of_position_folder");	
			
	       	System.out.println("Num of Nodes                :" + num_Nodes);		
	       	System.out.println("Simulation Time Units       :" + sim_Time);		
	       	System.out.println("Total Population            :" + num_People);		
	       	System.out.println("Sample Size                 :" + sample_Size);
	       	System.out.println("K Value                     :" + k_Value);
	        System.out.println("CI			                :" + val_Ci);
	       	System.out.println("Epsilon                     :" + val_Epsilon);
	       	System.out.println("Is Randomly Generated Graph :" + is_Random_Graphs);
	       	System.out.println("Path of network folder      :" + path_network_folder);
	       	System.out.println("Path of position folder     :" + path_position_folder);	       	
	       }
		   catch(FileNotFoundException ex)
	       {
	          // System.out.println(" \n Config File is not found, please pass it through constructor!! \n");
		   }
	   	   catch(IOException ex)
	       {
	            ex.printStackTrace();
	  	   }
	       catch(Exception e)
	       {
		     // System.out.println(" \n Error occured, please try again!! \n ");
			  return;
		   }
	       finally
	       {
	              fr.close();                
	       }         

	   }/* End Of read_configFile() */
}
