package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apitest.Contact;

public class ContactToJsonTests
implements TestCase {

	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	@Override
	public String getHeader() {
		return "Contact to JSON Tests";
	}
	
	private void testNoAttribs() {
	
		try {
			this.testsRun++;
			String expectStr = 
					"[\n" + 
					"   {\n" + 
					"      \"email\": \"jk@somewhere.com\"\n" + 
					"   }\n" + 
					"]";
			Contact con = new Contact( "jk@somewhere.com", new HashMap<String,String>() );
			String conStr = con.toJson();
			if( ! expectStr.equals( conStr )) {
				throw new Exception( "simplest Contact.toJson() match fail" );
			}
		} catch( Exception ex ) {
			this.exList.add( ex );
		}
	}
	
	private void test2Attribs() {
	
		try {
			this.testsRun++;
			String expectStr = 
					"[\n" + 
					"   {\n" + 
					"      \"email\": \"jk@somewhere.com\",\n" + 
					"      \"address\": \"1234 address lane\",\n" + 
					"      \"phone\": \"303-555-1212\"\n" + 
					"   }\n" + 
					"]";
			HashMap<String, String> attribs = new HashMap<>();
			attribs.put( "address", "1234 address lane" );
			attribs.put( "phone", "303-555-1212" );
			Contact con = new Contact( "jk@somewhere.com", attribs );
			String conStr = con.toJson();
			if( ! expectStr.equals( conStr )) {
				throw new Exception( "2 attribute Contact.toJson() match fail" );
			}
		} catch( Exception ex ) {
			this.exList.add( ex );
		}
	}

	@Override
	public void run() {
		
		this.testNoAttribs();
		this.test2Attribs();
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
