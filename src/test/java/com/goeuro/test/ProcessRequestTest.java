package com.goeuro.test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.goeuro.test.csv.CsvHelper;
import com.goeuro.test.exception.HttpClientException;
import com.goeuro.test.http.HttpClientHelper;
import com.goeuro.test.model.GeoPositionModel;
import com.goeuro.unit_test.utils.RandomUtil;

public class ProcessRequestTest {
	
	private static final String TEST_CITY = "Berlin";
	
	private static final String JSON_RESPONSE = "[{\"_id\": 376217,\"name\": \"Berlin\",\"type\": \"location\","
	    + "\"geo_position\": {"
	     + "\"latitude\": 52.52437,"
	      + "\"longitude\": 13.41053}"
	      + "}]";

	@Mock
	private HttpClientHelper mockHttpClientHelper;
	
	@Mock
	private CsvHelper<GeoPositionModel> mockCsvWriter;
	
	private ProcessRequest processRequest;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		processRequest = new ProcessRequest();
		processRequest.setHttpClientHelper(mockHttpClientHelper);
		processRequest.setCsvWriter(mockCsvWriter);
	}
	
	@Test
	public void testProcessGoEuroRequest_WhenHttpClientExceptionIsThrown() throws HttpClientException {
		// Setup the SUT
		when(mockHttpClientHelper.getCityDetails(anyString())).thenThrow(new HttpClientException("error"));

		// Run the test
		boolean expected = false;
		boolean actual = processRequest.processGoEuroRequest(TEST_CITY);
		assertEquals(expected, actual);
		
		// Verify mock interactions
		verify(mockHttpClientHelper, times(1)).getCityDetails(anyString());
		verifyZeroInteractions(mockCsvWriter);
	}

	@Test
	public void testProcessGoEuroRequest_WhenIOExceptionIsThrown() throws HttpClientException, IOException {
		// Setup the SUT
		when(mockHttpClientHelper.getCityDetails(anyString())).thenReturn(JSON_RESPONSE);
		when(mockCsvWriter.writeToCsvFile(anyString(), anyList())).thenThrow(new IOException());

		// Run the test
		boolean expected = false;
		boolean actual = processRequest.processGoEuroRequest(TEST_CITY);
		assertEquals(expected, actual);
		
		// Verify mock interactions
		verify(mockHttpClientHelper, times(1)).getCityDetails(anyString());
		verify(mockCsvWriter, times(1)).writeToCsvFile(anyString(), anyList());
	}

	@Test
	public void testProcessGoEuroRequest_WhenNoExceptionsAreThrown() throws HttpClientException, IOException {
		// Setup the SUT
		when(mockHttpClientHelper.getCityDetails(anyString())).thenReturn(JSON_RESPONSE);
		when(mockCsvWriter.writeToCsvFile(anyString(), anyList())).thenReturn(true);

		// Run the test
		boolean expected = true;
		boolean actual = processRequest.processGoEuroRequest(TEST_CITY);
		assertEquals(expected, actual);
		
		// Verify mock interactions
		verify(mockHttpClientHelper, times(1)).getCityDetails(anyString());
		verify(mockCsvWriter, times(1)).writeToCsvFile(anyString(), anyList());
	}
	
	@Test
	public void testProcessJsonResponse_WhenJsonArrayIsNull() throws IOException {
		List<GeoPositionModel> actual = processRequest.processJsonResponse(TEST_CITY, null);
		assertEquals(actual.isEmpty(), true);
	}

	@Test
	public void testProcessJsonResponse_WhenJsonArrayIsEmpty() throws IOException {
		List<GeoPositionModel> actual = processRequest.processJsonResponse(TEST_CITY, new JSONArray());
		assertEquals(actual.isEmpty(), true);
	}

	@Test
	public void testProcessJsonResponse_WhenJsonArrayIsNotEmpty() throws IOException {
		// Setup the SUT
		JSONArray jsonArray = generateJsonArray();
		
		// Run the test
		List<GeoPositionModel> actual = processRequest.processJsonResponse(TEST_CITY, jsonArray);
		assertEquals(actual.isEmpty(), false);
		assertEquals(actual.size(), jsonArray.length());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertJsonObjectToGeoPositionModel_WhenJsonObjectIsNull() {
		processRequest.convertJsonObjectToGeoPositionModel(null);
	}

	@Test
	public void testConvertJsonObjectToGeoPositionModel_WhenJsonObjectIsNotNull() {
		JSONObject jsonObject = generateJsonObject();
		GeoPositionModel actual = processRequest.convertJsonObjectToGeoPositionModel(jsonObject);
		assertNotNull(actual);
		assertGeoPositionModelIsAccurate(actual, jsonObject);
	}
	
	private JSONArray generateJsonArray() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(generateJsonObject());
		jsonArray.put(generateJsonObject());
		
		return jsonArray;
	}
	
	private JSONObject generateJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ProcessRequest.JSON_KEY_ID, RandomUtil.getRandomInt());
		jsonObject.put(ProcessRequest.JSON_KEY_NAME, RandomUtil.getRandomString(10));
		jsonObject.put(ProcessRequest.JSON_KEY_TYPE, RandomUtil.getRandomString(20));
		
		JSONObject geoPositionObject = new JSONObject();
		geoPositionObject.put(ProcessRequest.JSON_KEY_LATITUDE, RandomUtil.getRandomDouble());
		geoPositionObject.put(ProcessRequest.JSON_KEY_LONGITUDE, RandomUtil.getRandomDouble());
		jsonObject.put(ProcessRequest.JSON_KEY_GEO_POSITION, geoPositionObject);
		
		return jsonObject;
	}
	
	private void assertGeoPositionModelIsAccurate(GeoPositionModel geoPositionModel, JSONObject jsonObject) {
		assertEquals(geoPositionModel.getId(), jsonObject.getInt(ProcessRequest.JSON_KEY_ID));
		assertEquals(geoPositionModel.getName(), jsonObject.getString(ProcessRequest.JSON_KEY_NAME));
		assertEquals(geoPositionModel.getType(), jsonObject.getString(ProcessRequest.JSON_KEY_TYPE));
		JSONObject geoPositionObject = jsonObject.getJSONObject(ProcessRequest.JSON_KEY_GEO_POSITION);
		assertEquals(geoPositionModel.getLatitude(), geoPositionObject.getDouble(ProcessRequest.JSON_KEY_LATITUDE), 0);
		assertEquals(geoPositionModel.getLongitude(), geoPositionObject.getDouble(ProcessRequest.JSON_KEY_LONGITUDE), 0);
	}

}
