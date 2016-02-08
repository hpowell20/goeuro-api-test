package com.goeuro.test.csv;

import java.util.ArrayList;
import java.util.List;

import com.goeuro.test.model.GeoPositionModel;

public class GeoPositionModelDataConverter implements DataConverter<GeoPositionModel> {

	private static final String[] FILE_HEADERS = { "id", "name", "type", "latitude", "longitude" };
	
	@Override
	public String[] getHeaders() {
		return FILE_HEADERS;
	}

	@Override
	public List<String> toRecord(GeoPositionModel geoPositionModel) {
		List<String> record = new ArrayList<String>();
		record.add(String.valueOf(geoPositionModel.getId()));
		record.add(geoPositionModel.getName());
		record.add(geoPositionModel.getType());
		record.add(String.valueOf(geoPositionModel.getLatitude()));
		record.add(String.valueOf(geoPositionModel.getLongitude()));
		
		return record;
	}
}
