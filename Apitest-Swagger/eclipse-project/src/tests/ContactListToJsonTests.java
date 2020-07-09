package tests;

import java.util.ArrayList;
import java.util.List;

import apitest.ContactList;

public class ContactListToJsonTests
implements TestCase {

	ArrayList<Exception> exList = new ArrayList<>();
	int testsRun = 0;
	
	@Override
	public String getHeader() {
		return "ContactList to JSON Tests";
	}
	
	private void testNoMembers() {
	
		try {
			this.testsRun++;
			String expectStr = 
					"[\n" + 
					"   {\n" + 
					"      \"name\": \"test1\"\n" + 
					"   }\n" + 
					"]";
			ContactList cl = new ContactList( "test1" );
			String clStr = cl.toJson();
			if( ! expectStr.equals( clStr )) {
				throw new Exception( "simplest ContactList.toJson() match fail" );
			}

		} catch( Exception ex ) {
			this.exList.add( ex );
		}
	}
	
	private void test2Members() {
	
		try {
			this.testsRun++;
			String expectStr = 
					"[\n" + 
					"   {\n" + 
					"      \"name\": \"test2\",\n" + 
					"      \"member\": \"jk@somewhere.com\",\n" + 
					"      \"member\": \"j2k@nowhere.com\"\n" + 
					"   }\n" + 
					"]";
			ContactList cl = new ContactList( "test2" );
			cl.addMember( "jk@somewhere.com" );
			cl.addMember( "j2k@nowhere.com" );
			String clStr = cl.toJson();
			if( ! expectStr.equals( clStr )) {
				throw new Exception( "2 member ContactList.toJson() match fail" );
			}

		} catch( Exception ex ) {
			this.exList.add( ex );
		}
	}

	@Override
	public void run() {
		
		this.testNoMembers();
		this.test2Members();
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
