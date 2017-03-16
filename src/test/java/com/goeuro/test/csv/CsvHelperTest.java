package com.goeuro.test.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.goeuro.test.model.GeoPositionModel;
import com.goeuro.unit_test.utils.RandomUtil;

public class CsvHelperTest {

	private static final String UNIT_TEST_FILE_NAME = "unit-test";
	
	@Test(expected=IllegalArgumentException.class)
	public void testWriteToCsvFile_WhenBaseFileNameListIsNull() throws IOException {
		// Setup  the SUT
		final CsvHelper<TestModel> csvHelper = new CsvHelper<TestModel>(new TestDataConverter());
		csvHelper.writeToCsvFile(null, createTestModelList());
	}

	@Test
	public void testWriteToCsvFile_WhenDataObjectsListIsNull() throws IOException {
		boolean expected = false;
		
		final CsvHelper<TestModel> csvHelper = new CsvHelper<TestModel>(new TestDataConverter());
		boolean actual = csvHelper.writeToCsvFile(UNIT_TEST_FILE_NAME, null);
		assertEquals(expected, actual);
	}

	@Test
	public void testWriteToCsvFile_WhenListIsEmpty() throws IOException {
		boolean expected = false;
		
		final CsvHelper<TestModel> csvHelper = new CsvHelper<TestModel>(new TestDataConverter());
		boolean actual = csvHelper.writeToCsvFile(UNIT_TEST_FILE_NAME, Collections.emptyList());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWriteToCsvFile_WhenGeoPositionModelListIsNotEmpty() throws IOException {
		// Setup  the SUT
		boolean expected = true;
		List<GeoPositionModel> testList = Arrays.asList(createTestGeoPositionModel(), createTestGeoPositionModel());
		
		// Run the test
		final CsvHelper<GeoPositionModel> csvHelper = new CsvHelper<GeoPositionModel>(new GeoPositionModelDataConverter());
		boolean actual = csvHelper.writeToCsvFile(UNIT_TEST_FILE_NAME, testList);
		assertEquals(expected, actual);
		
		// Clean up the output file
		File file = new File(csvHelper.setCsvFileName(UNIT_TEST_FILE_NAME));
		file.delete();	
	}	

	// Private Helper Methods
	private List<TestModel> createTestModelList() {
		return Arrays.asList(new TestModel(), new TestModel());
	}
	
	private GeoPositionModel createTestGeoPositionModel() {
		return GeoPositionModel.builder()
				.withId(RandomUtil.getRandomInt())
				.withName(RandomUtil.getRandomString(25))
				.withType(RandomUtil.getRandomString(10))
				.withLatitude(RandomUtil.getRandomDouble())
				.withLongitude(RandomUtil.getRandomDouble())
				.build();
	}
}
