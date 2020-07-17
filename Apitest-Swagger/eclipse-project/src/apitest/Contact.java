package apitest;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class Contact {

	private static final String KEY_ATTRIB = "email";
	
	private String key;
	private LinkedHashMap<String,String> values = new LinkedHashMap<>();
	
	public Contact(String email, String name, String address, String phone) {
		this.key = email;
		this.values.put( KEY_ATTRIB, email );
		this.values.put( "name", name );
		this.values.put( "address", address );
		this.values.put( "phone", phone );
	}
	
	public Contact( String _key, HashMap<String,String> _values ) {
		this.key = _key;
		this.values.put( KEY_ATTRIB, _key );
		this.values.putAll( _values );
	}
	
	public String getKey() {
	    return this.key;
	}
	
	public HashMap<String,String> getValues() {
		HashMap<String,String> retVal = new HashMap<>();
		retVal.putAll( this.values );
		return retVal;
	}
	
	public void toJson( JsonWriter _writer ) throws Exception {
	
		_writer.beginObject();
	    for( String attrib : this.values.keySet() ) {
	    	_writer.name( attrib ).value( this.values.get( attrib ));
	    }
	    _writer.endObject();
	}
	
	public static Contact fromJson( String _key, JsonReader reader ) throws Exception {
	
		Contact returnVal = null;
		
		reader.beginObject();
		LinkedHashMap<String,String> valueMap = new LinkedHashMap<>();
		while( reader.hasNext() ) {
			String key = reader.nextName();
			if( reader.peek() == JsonToken.STRING ) {
				String value = reader.nextString();
				valueMap.put( key, value );
			} else {
			    reader.skipValue();
			}
		}
		reader.endObject();
		
		if( _key!=null ) {
			returnVal = new Contact( _key, valueMap );
		}
		
		return returnVal;
	}
}
