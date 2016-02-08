package com.goeuro.test.http;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.goeuro.test.exception.HttpClientException;
import com.goeuro.unit_test.utils.RandomUtil;

public class HttpClientHelperTest {

	private HttpClientHelper httpClientHelper;
	
	@Mock
	private CloseableHttpClient mockHttpClient;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		httpClientHelper = new HttpClientHelper();
		httpClientHelper.setHttpClient(mockHttpClient);
	}
	
	@Test
	public void testGetCityDetails_WhenResultFound() throws HttpClientException, IOException {
		// Setup the SUT
		CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
		HttpEntity mockEntity = mock(HttpEntity.class);
		when(mockResponse.getEntity()).thenReturn(mockEntity);
		when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(mockResponse);
		
		httpClientHelper.getCityDetails(RandomUtil.getRandomString(20));
		
		// Verify mock interactions
		verify(mockHttpClient, times(1)).execute(any(HttpUriRequest.class));
		verify(mockResponse, times(1)).getEntity();
	}
	
	@Test(expected=HttpClientException.class)
	public void testGenerateGetRequest_WhenUrlIsNull() throws HttpClientException {
		httpClientHelper.generateGetRequest(null);
	}

	@Test
	public void testGenerateGetRequest_WhenUrlIsNotEmpty() throws HttpClientException {
		HttpGet actual = httpClientHelper.generateGetRequest(RandomUtil.getRandomUrl().toString());
		assertNotNull(actual);
		assertEquals(actual.getHeaders(HttpHeaders.CONTENT_TYPE).length, 1);
	}
	
	@Test(expected=HttpClientException.class)
	public void testDoRequest_WhenRequestIsNull() throws HttpClientException {
		httpClientHelper.doRequest(null);
	}
	
	@Test(expected=HttpClientException.class)
	public void testDoRequest_WhenReponseEntityIsNull() throws HttpClientException, IOException {
		// Setup the SUT
		CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
		when(mockResponse.getEntity()).thenReturn(null);
		when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(mockResponse);
		
		// Run the test
		HttpGet request = httpClientHelper.generateGetRequest(RandomUtil.getRandomUrl().toString());
		httpClientHelper.doRequest(request);
	}
	
	@Test(expected=HttpClientException.class)
	public void testDoRequest_WhenIoExeptionIsThrown() throws HttpClientException, IOException {
		// Setup the SUT
		when(mockHttpClient.execute(any(HttpUriRequest.class))).thenThrow(new IOException());
		
		// Run the test
		HttpGet request = httpClientHelper.generateGetRequest(RandomUtil.getRandomUrl().toString());
		httpClientHelper.doRequest(request);
	}
}
