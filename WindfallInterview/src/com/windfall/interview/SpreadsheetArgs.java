package com.windfall.interview;

/**
 * Class that encapsulates command line arguments.  Not much is used at this time - 
 * the output file and algorithm attributes were written in when the author thought
 * that these may be required to complete the assigned task (efficiently).
 */
public class SpreadsheetArgs {

	private String inputFile = "";
	private String outputFile = "";
	private String algorithm = "";
	
	public SpreadsheetArgs( String[] args ) throws Exception {
		if( args == null ) throw new Exception( "Input arguments are null" );
		if( args.length == 0 ) throw new Exception( "no arguments specified" );
		
		this.inputFile = args[0];
	}
	
	public String getInputFileName() {
		return this.inputFile;
	}
	
	public String getOutputFileName() {
		return this.outputFile;
	}
	
	public String getAlgorithm() {
		return this.algorithm;
	}
}
