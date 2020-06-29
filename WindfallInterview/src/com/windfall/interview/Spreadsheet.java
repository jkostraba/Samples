package com.windfall.interview;

/** 
 * Class that is the required main/entry point for our application.
 *
 */
public class Spreadsheet {

	private static String usage = new String(
			"Usage: Spreadsheet <input file name>\n" );
	
	// This testableMain() will return 0 when the app runs without errors and <0 on an error.
	// The testable method is defined in order to facilitate testing without using a ProcessBuilder
	// or other command line Exec() - these tend to be platform dependent because path names and
	// java environments vary from system to system and from architecture to architecture.
	//
	public static int testableMain(String[] args ) {
		
		int exitValue = -1;
		
		try {
			
			SpreadsheetArgs parsedArgs = new SpreadsheetArgs(args);
			SpreadsheetSolver solver = new SpreadsheetSolver( parsedArgs );
			exitValue = solver.run();
			
		} catch( Exception ex ) {
			
			System.out.println("Exception: " + ex);
			System.out.println(usage);
		}
		
		return exitValue;
	}
	
	// This main() is the required, traditional entry point into the application.
	// It invokes the testableMain that returns an integer value
	//
	public static void main(String[] args) {
		
		int exitValue = testableMain(args);
		System.exit(exitValue);
	}
}
