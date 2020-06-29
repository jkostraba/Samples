package com.windfall.interview.tests;

import com.windfall.interview.Spreadsheet;

public class CaseFileNotFound extends TestCase {

	public CaseFileNotFound() {
		this.caseName = "File not found";
	}
	
	public void run() {
		
		String[] testArgs = new String[1];
		testArgs[0] = /* intentionally not present */ "testFiles/6.in.txt";
		
		super.redirectStdout();
		super.returnVal = Spreadsheet.testableMain( testArgs );
		// testableMain() catches any exception that can be caught, so we don't need to
		// duplicate that here prior to worrying about restoring stdout
		super.restoreStdout();
		
		boolean outputMatch = super.stdoutMatch("testFiles/6.out.txt");
		
		if( (super.returnVal==-1) && outputMatch ) {
			super.returnText = TestCase.OK;
		} else if (! outputMatch ) {
			super.returnText = TestCase.Error + "output mismatch";
		} else {
			super.returnText = TestCase.Error + "failed return status";
		}
	}
}
