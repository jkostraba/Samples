package tests;

import java.util.ArrayList;
import java.util.List;

public class GetContactTests
implements TestCase {

	private static final String QUERY_URL = "http://localhost:8080/apitest-swagger/contact/";
	private static final String JK_PARAMS = "?email=jk@somewhere.com";
	private static final String NOMATCH_PARAMS = "?email=thereisnorecordwiththisemail@somewhere.com";
	private static final String NOKEY_PARMS = "?nokeyhere=nada";
	
	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	private void normalPath() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL + JK_PARAMS );
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
		if( ! queryStr.contains("John"))
		    throw new Exception( "HTTP response body must contain the specified contact" );
	}

	private void noMatchTest() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL + NOMATCH_PARAMS );
		int code = requ.doGet();
		String queryStr = requ.getResponseBody();
		
		// asserts - checking the return status and that a JSON document is in the
		// response body
		if( code != 404 ) 
			throw new Exception( "HTTP response code should be 404, received " + code );
		if( ! queryStr.trim().startsWith( "[]" ))
			throw new Exception( "HTTP response body must be an empty JSON doc" );
	}
	
	private void noKeyTest() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL + NOKEY_PARMS );
		int code = requ.doGet();
		String queryStr = requ.getResponseBody();
		
		// asserts - checking the return status and that a JSON document is in the
		// response body
		if( code != 404 ) 
			throw new Exception( "HTTP response code should be 404, received " + code );
		if( ! queryStr.trim().startsWith( "[]" ))
			throw new Exception( "HTTP response body must be an empty JSON doc" );
	}
	
	@Override
	public String getHeader() {
		return "GET contact tests";
	}

	@Override
	public void run() {
		try {
			normalPath();
			noMatchTest();
			noKeyTest();
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
