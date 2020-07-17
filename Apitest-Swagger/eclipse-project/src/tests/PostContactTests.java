package tests;

import java.util.ArrayList;
import java.util.List;

public class PostContactTests
implements TestCase {

	private static final String QUERY_URL = "http://localhost:8080/apitest-swagger/contact/";
	private static final String NEW_PARAMS = "?email=newdude@somewhere.com";
	
	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	private void normalPath() throws Exception {
	
		++testsRun;
			
		String contactStr = 
				"[\n" + 
				"   {\n" + 
				"      \"email\": \"newdude@somewhere.com\",\n" + 
				"      \"address\": \"1234 address lane\",\n" + 
				"      \"phone\": \"303-555-1212\"\n" + 
				"   }\n" + 
				"]";
					
		ApiRequest requ = new ApiRequest( QUERY_URL + NEW_PARAMS );
		requ.doPost(contactStr);
	
		int code = requ.doGet();
		if( code!=200 )
			throw new Exception( "Failed to find contact after post, expected 200, received " + code );
	}

	@Override
	public String getHeader() {
		return "POST contacts tests";
	}

	@Override
	public void run() {
		try {
			normalPath();
		} catch( Exception ex ) {
			exList.add( ex );
		}
	}

	@Override
	public List<String> getErrors() {
		ArrayList<String> retVal = new ArrayList<>();
		for( Exception ex : this.exList ) {
			retVal.add( ex.getMessage() );
		}
		
		return retVal;
	}

	@Override
	public int getTestCount() {
		return this.testsRun;
	}

	@Override
	public int getFailCount() {
		return this.exList.size();
	}
}
