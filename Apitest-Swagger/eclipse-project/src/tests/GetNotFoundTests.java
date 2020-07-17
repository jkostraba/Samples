package tests;

import java.util.ArrayList;
import java.util.List;

public class GetNotFoundTests
implements TestCase {

	private static final String QUERY_URL = "http://localhost:8080/apitest-swagger/foo";
	
	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	// validate that the server is up and will throw a 404 for undefined paths
	private void normalPath() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL );
		int code = requ.doGet();
		
		// asserts - checking the return status and that a JSON document is in the
		// response body
		if( code != 404 ) 
			throw new Exception( "HTTP response code should be 404, received " + code );
	}

	@Override
	public String getHeader() {
		return "GET undefined path returns 404";
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
