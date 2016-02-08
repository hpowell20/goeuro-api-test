package com.goeuro.test.csv;

import java.util.ArrayList;
import java.util.List;

public class TestDataConverter implements DataConverter<TestModel> {

	private static final String[] FILE_HEADERS = { "unit", "test" };
	
	@Override
	public String[] getHeaders() {
		return FILE_HEADERS;
	}

	@Override
	public List<String> toRecord(TestModel testModel) {
		List<String> record = new ArrayList<String>();
		record.add(testModel.getName());
		record.add(testModel.getValue());
		
		return record;
	}

}
