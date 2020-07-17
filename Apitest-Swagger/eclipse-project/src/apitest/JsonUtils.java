package apitest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.google.gson.stream.JsonWriter;

/** class that wraps up creation and closure of a JsonWriter.  This series
 *  of instructions was being used enough times to make a utility class 
 *  attractive.  There is a bit of a pain in that the stream that we are
 *  creating for the class to write into isn't query-able from the JsonWriter,
 *  so we also create an association map to be able "get()" the outputstream
 *  associated with the writer.
 */
public class JsonUtils {

	private static HashMap<Object, ByteArrayOutputStream> writerStreams = new HashMap<>();

	public static JsonWriter makeWriter() throws Exception {
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(baos, "UTF-8"));
		JsonUtils.writerStreams.put( (Object)writer, baos );

		writer.setIndent("   ");
		writer.beginArray();
		
		return writer;
	}
	
	public static String closeWriter(JsonWriter writer) throws Exception {
	
		writer.endArray();
		writer.flush();
		
		String jsonStr = "";
		ByteArrayOutputStream baos = JsonUtils.writerStreams.get( (Object)writer );
		if( baos!=null ) {
			jsonStr = baos.toString();
		}
		
		writer.close();
		return jsonStr;
	}
}
