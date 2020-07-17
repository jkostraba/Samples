package apitest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.stream.JsonWriter;


@WebServlet("/contacts")
public class ContactsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public ContactsServlet() {
	   super();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
		
			JsonWriter writer = JsonUtils.makeWriter();
	    
			List<Contact> contacts = ContactStorage.getInstance().getContacts();
			for( Contact contact : contacts ) {
				contact.toJson( writer );
			}
	    
			String jsonStr = JsonUtils.closeWriter(writer);
			response.getWriter().println(jsonStr);

		} catch( Exception ex ) {
			// Re-throw any exceptions as IOExceptions to comply with the servlet definition
			throw new IOException( ex.getMessage() );
		}
	}
	
	@Override
    public void init() throws ServletException {}

    public void destroy() {}
}

