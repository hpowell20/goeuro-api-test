package com.goeuro.test.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.goeuro.test.exception.HttpClientException;
import com.goeuro.test.utils.AppProperties;

public class HttpClientHelper {
	
	protected static final String CONTENT_TYPE_JSON = "application/json";
	
	private CloseableHttpClient httpClient;
	
	public HttpClientHelper() {
		httpClient = HttpClients.createDefault();
	}
	
	public String getCityDetails(String cityName) throws HttpClientException {
		String url = AppProperties.getInstance().getBaseUrl() + cityName;
		return doGet(url);
	}
			
	// Private Helper Methods
	private String doGet(String url) throws HttpClientException {		
		return doRequest(generateGetRequest(url));
	}
	
	protected HttpGet generateGetRequest(String url) throws HttpClientException {
		if (url == null) {
			throw new HttpClientException("url is null");
		}

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader(getApplicationJsonHeader());
		
		return httpGet;
	}
	
	protected String doRequest(HttpUriRequest request) throws HttpClientException {
		if (request == null) {
			throw new HttpClientException("Request object is null");
		}
		
		try (CloseableHttpResponse response = httpClient.execute(request)) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new HttpClientException(String.format("Request from %s returned status code %d.  Please confirm the URL is still available.",
						request.getURI().toString(), statusCode));
			}
		
			return getResponseEntityValue(response);
		} catch (IOException e) {
			throw new HttpClientException(e);
		}
	}
	
	private static String getResponseEntityValue(CloseableHttpResponse response) throws IOException {
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity == null) {
			throw new IOException("Response is null");
		}
	
		return EntityUtils.toString(httpEntity);
	}
	
	private static Header getApplicationJsonHeader() {
		return new BasicHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
	}

	// User by unit tests
	protected void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
