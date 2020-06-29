package com.windfall.interview.tests;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * Class that encapsulates the common attributes and methods that most test cases will
 * use.  For example: the name of the test case, means to redirect stdout to a string,
 * and storing a return value.
 *
 */
public abstract class TestCase {

	// these 2 strings are often implemented so that the text output is perfectly consistent
	// and parse-able by automated tools
	protected final static String OK = "OK";
	protected final static String Error = "Error! ";
	
	protected int returnVal = -1;
	protected String returnText = "";
	
	public int getReturnVal() {
		return this.returnVal;
	}
	
	public String getReturnText() {
		return this.returnText;
	}
	
	protected String caseName = "";
	
	public String getCaseName( ) {
		return this.caseName;
	}
	
	private PrintStream stdout = null;
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	protected String stdoutString = "";
	
	// just like it sounds - seems a little fancy, but is actually very straightforward so long
	// as we are careful to not close System.out and restore the stdout, even in the case
	// of an exception
	protected void redirectStdout() {
		System.out.flush();
		this.stdout = System.out;
		PrintStream ps = new PrintStream(this.bos);
		System.setOut( ps );
	}
	
	protected void restoreStdout() {
		System.out.flush();
		this.stdoutString = bos.toString();
		if( this.stdout != null ) {
			System.setOut( this.stdout );
		}
	}
	
	// Method that tests the .stdoutStr against the contents of a file and returns
	// true when the contents match (same sequence of characters), false otherwise
	//
	protected boolean stdoutMatch( String expectedStdoutFile ) {
		
		String expectedStr = "";
		
		try {
			
			File expectedOutput = new File(expectedStdoutFile);
			BufferedReader br = new BufferedReader( new FileReader( expectedOutput ));
			String line;

			while( (line = br.readLine()) != null ) {
				expectedStr += line + "\r\n";
			}
			
			br.close();
			
		} catch (Exception ex ) {
			System.out.println( "Failed to read expected string file with exception: " + ex );
			// fall through to likely mismatch because reading the string source file failed
		}
		
		return this.stdoutString.equals( expectedStr );		
	}
	
	// Each test case is required to implement a run() method
	public abstract void run();
}
