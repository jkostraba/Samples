package apitest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


@WebServlet("/contactLists")
/** Servlet that fulfills contactLists queries.  This reports
 *  a list of all of the contact lists.  The lists are 
 *  reported in the body of the response.
 */
public class ContactListsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String URI_Prefix = "/apitest-java/contactLists/";
	private final String EMPTY_JSON = "[]";
	
	public ContactListsServlet() {
	   super();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			String requestUrl = request.getRequestURI();
			String decodedUrl = java.net.URLDecoder.decode(requestUrl, "UTF-8");
		
			// use the URI to determine the record being queried - 
			//   1. parse out the name from the end of the URI, 
			//   2. query the contact from the specified name/key, 
			//   3. then convert it to json
			//   4. emit the json string
			//
			if( decodedUrl.length() > URI_Prefix.length() ) {
				String name = decodedUrl.substring(URI_Prefix.length());
				Contact contact = ContactStorage.getInstance().get(name);
				String jsonStr = EMPTY_JSON;
				JsonWriter writer = JsonUtils.makeWriter();
				if( contact!=null ) {
					contact.toJson( writer );
					jsonStr = JsonUtils.closeWriter(writer);
				}
				response.getWriter().println(jsonStr);
			} else {
				response.getWriter().println(EMPTY_JSON);
			}
		} catch( Exception ex ) {
			// Re-throw any exceptions as IOExceptions to comply with the servlet definition
			throw new IOException( ex.getMessage() );
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		InputStream is = request.getInputStream();
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		
		// this will parse one JSON object from the array specified in the body of the
		// Http POST.
		//
		try {
			reader.beginArray();
			Contact contact = Contact.fromJson( "", reader );
			ContactStorage.getInstance().put( contact );
			reader.endArray();
		} catch( Exception ex ) {
			// Re-throw any exceptions as IOExceptions to comply with the servlet definition
			throw new IOException( ex.getMessage());
		} finally {
			if( reader!=null ) reader.close();
		}
	}
	
	@Override
    public void init() throws ServletException {}

    public void destroy() {}
}

