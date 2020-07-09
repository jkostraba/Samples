package apitest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ContactList {

	private static final String NAME_ATTRIB = "name";
	private static final String MEMBER_ATTRIB = "member";
	
	private String name;
	private ArrayList<String> members = new ArrayList<>();
	
	public ContactList(String _name) {
		this.name = _name;
	}
	
	public ContactList( String _name, List<String> _members ) {
		this.name = _name;
		this.members.addAll( _members );
	}
	
	public String getName() {
	    return this.name;
	}
	
	public void addMember( String _member ) {
		this.members.add( _member );
	}
	
	public void removeMember( String _member ) {
	    this.members.remove( _member );
	}
	
	public List<String> getMembers() {
	    List<String> retVal = new ArrayList<String>();
	    retVal.addAll( this.members );
	    return retVal;
	}
	
	public String toJson() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    JsonWriter writer = new JsonWriter(new OutputStreamWriter(baos, "UTF-8"));
	    writer.setIndent("   ");
	    writer.beginArray();
	    
	    writer.beginObject();
	    writer.name( NAME_ATTRIB ).value( this.name );
	    for( String member : this.members ) {
	    	writer.name( MEMBER_ATTRIB).value( member );
	    }
	    writer.endObject();
	    
	    writer.endArray();
	    writer.flush();
	    
	    String jsonStr = baos.toString();
	    writer.close();
	    
		return jsonStr;
	}
	
	public static ContactList fromJson( JsonReader reader ) throws Exception {
		ContactList returnVal = null;
		
		reader.beginObject();
		
		// gather the list name and members - if the name is not present, then
		// we will return a null (no new contactList object is created)
		String name = null;
		ArrayList<String> members = new ArrayList<>();
		
		// iterate through the json object - gather attribs named "name" and "member"
		while( reader.hasNext() ) {
			String key = reader.nextName();
			if( reader.peek() == JsonToken.STRING ) {
				String value = reader.nextString();
				if( key.contentEquals( NAME_ATTRIB )) {
					name = value;
				} else
				if( key.contentEquals( MEMBER_ATTRIB )) {
					members.add( value );
				}
				// default - ignore/skip other key/value pairs
			} else {
			    reader.skipValue();
			}
		}
		reader.endObject();
		
		if( name!=null ) {
			returnVal = new ContactList( name, members );
		}

		return returnVal;
	}
}
