package tests;

import java.util.ArrayList;

public class TestMain {

	public static void main(String[] args) {

		ArrayList<TestCase> testCases = new ArrayList<>();
		testCases.add( new ContactListToJsonTests() );
		testCases.add( new ContactToJsonTests() );
		
		int totalTests = 0;
		int totalFails = 0;
		
		for( TestCase tc : testCases ) {
			System.out.println( tc.getHeader() );
			tc.run();
			totalTests += tc.getTestCount();
			totalFails += tc.getFailCount();
			System.out.println( "    tests=" + tc.getTestCount() + ", fails=" + tc.getFailCount() );
		}
		
		System.out.println( "\nTotal tests=" + totalTests + ", fails=" + totalFails );
		
		if( totalFails==0 ) {
		   System.exit( 0 );
		} else {
		   System.exit( -1 );
		}
	}
}
