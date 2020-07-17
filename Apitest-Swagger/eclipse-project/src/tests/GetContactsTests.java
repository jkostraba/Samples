package tests;

import java.util.ArrayList;
import java.util.List;

public class GetContactsTests
implements TestCase {

	private static final String QUERY_URL = "http://localhost:8080/apitest-swagger/contacts";
	
	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	private void normalPath() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL );
		int code = requ.doGet();
		String queryStr = requ.getResponseBody();
		
		// asserts - checking the return status and that a JSON document is in the
		// response body
		if( code != 200 ) 
			throw new Exception( "HTTP response code should be 200, received " + code );
		if( ! queryStr.trim().startsWith( "[" ))
			throw new Exception( "HTTP response body must begin with JSON [ character" );
		if( (! queryStr.trim().endsWith( "]" )) &&
		    (! queryStr.trim().endsWith( "]\n" )))
			throw new Exception( "HTTP response body must end with JSON ] character" );
	}

	@Override
	public String getHeader() {
		return "GET contacts tests";
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
