package tests;

import java.util.ArrayList;

public class TestMain {

	public static void main(String[] args) {

		ArrayList<TestCase> testCases = new ArrayList<>();
		testCases.add( new GetNotFoundTests() );
		testCases.add( new GetContactsTests() );
		testCases.add( new ContactListToJsonTests() );
		testCases.add( new ContactToJsonTests() );
		testCases.add( new GetContactTests() );
		// order of operations here - do post before delete so that the post
		// creates a record for delete to work on
		testCases.add( new PostContactTests() );
		testCases.add( new DeleteContactTests() );

		int totalTests = 0;
		int totalFails = 0;
		
		for( TestCase tc : testCases ) {
			System.out.println( tc.getHeader() );
			tc.run();
			totalTests += tc.getTestCount();
			totalFails += tc.getFailCount();
			System.out.println( "    tests=" + tc.getTestCount() + ", fails=" + tc.getFailCount() );
			for( String exStr : tc.getErrors() ) {
				System.err.println( "        " + exStr );
			}
		}
		
		String summaryStr = "\nTotal tests=" + totalTests + ", fails=" + totalFails;
		if( totalFails > 0 ) {
			System.err.println(summaryStr);
		} else {
			System.out.println(summaryStr);
		}
		
		if( totalFails==0 ) {
		   System.exit( 0 );
		} else {
		   System.exit( -1 );
		}
	}
}
