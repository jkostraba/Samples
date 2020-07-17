package tests;

import java.nio.charset.Charset;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class ApiRequest {

	private String URL;
	private int    responseCode;
	private String responseBody = "";
	private HttpUriRequest         httpRequest;
	private CloseableHttpResponse  httpResponse;
	
	public ApiRequest( String _URL ) throws Exception {
		this.URL = _URL;
	}
	
	public int doGet() throws Exception {
		this.httpRequest = new HttpGet( URL );
	    this.httpResponse = HttpClientBuilder.create().build().execute( this.httpRequest );
	    this.responseCode = this.httpResponse.getCode();
	    HttpEntity entity = this.httpResponse.getEntity();
	    this.responseBody = EntityUtils.toString( entity );
	    this.httpResponse.close();
	    return this.responseCode;
	}
	
	public int doPost(String _body) throws Exception {
		this.httpRequest = new HttpPost( URL );
		HttpEntity entity = new StringEntity(_body, Charset.defaultCharset());
		this.httpRequest.setEntity(entity);
		this.httpResponse = HttpClientBuilder.create().build().execute( this.httpRequest );
	    this.responseCode = this.httpResponse.getCode();
		return this.responseCode;
	}
	
	public int doDelete() throws Exception {
		this.httpRequest = new HttpDelete( URL );
		this.httpResponse = HttpClientBuilder.create().build().execute( this.httpRequest );
	    this.responseCode = this.httpResponse.getCode();
		return this.responseCode;
	}
	
	public int getResponseCode() { return this.responseCode; }
	public String getResponseBody() { return this.responseBody; }
}
