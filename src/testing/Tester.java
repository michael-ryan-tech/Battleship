package testing;

public class Tester {

	public static final boolean DEBUG = false;
	
	
	// http://stackoverflow.com/questions/2979383/java-clear-the-console
	
	/*
	 * function - clearConsole
	 * clears the console to hide previous player's ships
	 */
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    	System.out.println("\nCannot clear screen.\n");
	    }
	}
	
}


