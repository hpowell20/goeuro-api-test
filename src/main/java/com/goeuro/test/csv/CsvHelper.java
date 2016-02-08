package com.goeuro.test.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvHelper<T> {
	private DataConverter<T> dataConverter;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvHelper.class);
	private static final String FILE_EXTENSION_CSV = ".csv";
	
	public CsvHelper(DataConverter<T> dataConverter) {
		this.dataConverter = dataConverter;
	}
	
	public boolean writeToCsvFile(String baseFileName, List<T> dataObjectsList) throws IOException {
		if (dataObjectsList != null && !dataObjectsList.isEmpty()) {
			writeResults(setCsvFileName(baseFileName), 
						 CSVFormat.DEFAULT.withHeader(dataConverter.getHeaders()), 
						 dataObjectsList);

			return true;
		}
		
		return false;
	}
	
	// Helper Methods
	private void writeResults(String csvFileName, CSVFormat csvFileFormat, List<T> dataObjectsList)
			throws IOException {
		
		try (FileWriter fileWriter = new FileWriter(csvFileName)) {
			try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFileFormat)) {
				processRecords(dataObjectsList, csvPrinter);
				LOGGER.info("CSV file created at " + csvFileName);
			}
		}
	}
	
	protected String setCsvFileName(String baseFileName) {
		return baseFileName + FILE_EXTENSION_CSV;
	}
	
	private void processRecords(List<T> dataObjectsList, CSVPrinter csvPrinter) throws IOException {
		for (T item : dataObjectsList) {
			csvPrinter.printRecord(dataConverter.toRecord(item));
		}
	}
}
