package apitest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class Contact {

	private static final String KEY_ATTRIB = "name";
	
	private String key;
	private LinkedHashMap<String,String> values = new LinkedHashMap<>();
	
	public Contact(String name, String address, String phone) {
		this.key = name;
		this.values.put( KEY_ATTRIB, name );
		this.values.put( "address", address );
		this.values.put( "phone", phone );
	}
	
	public Contact( String _key, HashMap<String,String> _values ) {
		this.key = _key;
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
	
	public String toJson() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    JsonWriter writer = new JsonWriter(new OutputStreamWriter(baos, "UTF-8"));
	    writer.setIndent("   ");
	    writer.beginArray();
	    
	    writer.beginObject();
	    for( String key : this.values.keySet() ) {
	    	writer.name( key ).value( this.values.get( key ));
	    }
	    writer.endObject();
	    
	    writer.endArray();
	    writer.flush();
	    
	    String jsonStr = baos.toString();
	    writer.close();
	    
		return jsonStr;
	}
	
	public static Contact fromJson( JsonReader reader ) throws Exception {
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
		
		String key = valueMap.get(KEY_ATTRIB);
		if( key!=null ) {
			returnVal = new Contact( key, valueMap );
		}
		
		return returnVal;
	}
}
