package tests;

import java.util.ArrayList;
import java.util.List;

public class DeleteContactTests
implements TestCase {

	private static final String QUERY_URL = "http://localhost:8080/apitest-swagger/contact/";
	private static final String JK_PARAMS = "?email=newdude@somewhere.com";
	private static final String NOMATCH_PARAMS = "?email=thereisnorecordwiththisemail@somewhere.com";
	private static final String NOKEY_PARMS = "?nokeyhere=nada";
	
	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	private void normalPath() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL + JK_PARAMS );
		
		int getCode = requ.doGet();
		if( getCode!=200 )
			throw new Exception( "GET before DELETE must have/find a contact" );
			
		int delCode = requ.doDelete();
		if( delCode!=200 )
		    throw new Exception( "DELETE did not return 200, returned code was " + delCode );
		
		getCode = requ.doGet();
		if( getCode!=404 )
		    throw new Exception( "GET after DELETE must now return 404, but we received code=" + getCode );
	}

	private void noMatchTest() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL + NOMATCH_PARAMS );
		int code = requ.doDelete();
		if( code != 404 ) 
			throw new Exception( "HTTP response code should be 404, received " + code );
	}
	
	private void noKeyTest() throws Exception {
		this.testsRun++;
		ApiRequest requ = new ApiRequest( QUERY_URL + NOKEY_PARMS );
		int code = requ.doDelete();
		if( code != 404 ) 
			throw new Exception( "HTTP response code should be 404, received " + code );
	}
	
	@Override
	public String getHeader() {
		return "DELETE contact tests";
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
