package com.goeuro.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goeuro.test.csv.CsvHelper;
import com.goeuro.test.csv.GeoPositionModelDataConverter;
import com.goeuro.test.exception.HttpClientException;
import com.goeuro.test.http.HttpClientHelper;
import com.goeuro.test.model.GeoPositionModel;
import com.goeuro.test.utils.JsonUtil;

public class ProcessRequest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRequest.class);

	protected static final String JSON_KEY_ID = "_id";
	protected static final String JSON_KEY_NAME = "name";
	protected static final String JSON_KEY_TYPE = "type";
	protected static final String JSON_KEY_GEO_POSITION = "geo_position";
	protected static final String JSON_KEY_LONGITUDE = "longitude";
	protected static final String JSON_KEY_LATITUDE = "latitude";

	private HttpClientHelper httpClientHelper;
	private CsvHelper<GeoPositionModel> csvWriter;
	
	public ProcessRequest() {
		httpClientHelper = new HttpClientHelper();
		csvWriter = new CsvHelper<GeoPositionModel>(new GeoPositionModelDataConverter());
	}
	
	public boolean processGoEuroRequest(String cityName) {
		
		try {
			// Using the HTTP helper, return a string of the results
			String jsonResponse = httpClientHelper.getCityDetails(cityName);
				
			// Populate the domain model with the results
			JSONArray jsonArray = JsonUtil.getJsonArrayFromResponse(jsonResponse);
			List<GeoPositionModel> geoPositionsList = processJsonResponse(cityName, jsonArray);
			
			// Write to the CSV file
			csvWriter.writeToCsvFile(cityName, geoPositionsList);
			
			return true;
		} catch (HttpClientException | IOException hce) {
			LOGGER.error("Exception occured during web service request", hce);
		}
		
		return false;
	}

	// Private Helper Methods
	protected List<GeoPositionModel> processJsonResponse(String cityName, JSONArray jsonArray) throws IOException {
		if (jsonArray == null || jsonArray.length() == 0) {
			LOGGER.error("No results found for city name " + cityName);
			return Collections.emptyList();
		}

		List<GeoPositionModel> geoPositionsList = new ArrayList<GeoPositionModel>();

		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject item = jsonArray.getJSONObject(i);
		    geoPositionsList.add(convertJsonObjectToGeoPositionModel(item));
		}

		return geoPositionsList;
	}

	protected GeoPositionModel convertJsonObjectToGeoPositionModel(JSONObject jsonObject) {
		if (jsonObject == null) {
			throw new IllegalArgumentException("jsonObject");
		}
		
		// Extract the required JSON items
		int id = jsonObject.getInt(JSON_KEY_ID);
		String name = jsonObject.getString(JSON_KEY_NAME);
		String type = jsonObject.getString(JSON_KEY_TYPE);
		
		JSONObject geoPositionJson = jsonObject.getJSONObject(JSON_KEY_GEO_POSITION);
        double latitude = geoPositionJson.getDouble(JSON_KEY_LATITUDE);
        double longitude = geoPositionJson.getDouble(JSON_KEY_LONGITUDE);
        
        return GeoPositionModel.builder()
				.withId(id)
				.withName(name)
				.withType(type)
				.withLatitude(latitude)
				.withLongitude(longitude)
				.build();
	}
	
	protected void setHttpClientHelper(HttpClientHelper httpClientHelper) {
		this.httpClientHelper = httpClientHelper;
	}
	
	protected void setCsvWriter(CsvHelper<GeoPositionModel> csvWriter) {
		this.csvWriter = csvWriter;
	}
}
