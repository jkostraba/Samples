package com.windfall.interview.tests;

import java.util.ArrayList;

/**
 * Class that serves to iterate the test cases in our one test suite - the suite is
 * defined here by our cases.add() statements.
 */
public class TestMain {
	
	public static void main(String[] notUsed) {
		
		ArrayList<TestCase> cases = new ArrayList<TestCase>();
		
		cases.add( new CaseExpressionEvaluator() );
		cases.add( new CaseExpressionEvaluatorWithWhitespace() );
		cases.add( new CaseSmallWithNoOperators() );
		cases.add( new CaseSmallWithCellReferences() );
		cases.add( new CaseSmallWithBadColumnRef() );
		cases.add( new CaseSmallWithBadRowRef() );
		cases.add( new CaseSmallWithCircularRef() );
		cases.add( new CaseFileNotFound() );
		cases.add( new CaseCsvFileInconsistentColumns() );
		cases.add( new CaseSmallWithWeirdWhitespace() );
		cases.add( new CaseSmallWith26Columns() );
		
		for( TestCase tc : cases ) {
			
			System.out.print( tc.caseName + ": " );
			try {
				
				tc.run();
				System.out.println(tc.getReturnText());
				
			} catch( Exception ex ) {
				System.out.println( ex );
			}
		}
	}
}
