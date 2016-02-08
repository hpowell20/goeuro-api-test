package com.goeuro.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class AppProperties {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppProperties.class);
	
	protected static final String PROPERTIES_FILE_NAME = "app.properties";
	
	// Key lookup values
	protected static final String BASE_URL_KEY = "base.url";
	
	private Properties properties = null;
	
	// Private constructor prevents instantiation
	private AppProperties() {
		properties = new Properties();
		loadPropertyFileContents(PROPERTIES_FILE_NAME);
	}

	static class AppPropertiesSingletonHolder { 		
		private static final AppProperties INSTANCE = new AppProperties();

		private AppPropertiesSingletonHolder() {

		}
	}

	public static AppProperties getInstance() {
		return AppPropertiesSingletonHolder.INSTANCE;
	}
	
	public String getBaseUrl() {
		return getPropertyValue(BASE_URL_KEY);
	}

	protected boolean loadPropertyFileContents(String propertiesFileName) {
		if (Strings.isNullOrEmpty(propertiesFileName)) {
			return false;
		}
		
		try (InputStream is = setInputStreamFromFile(propertiesFileName)) {
			if (is != null) {
				properties.load(is);
				return true;
			}
		} catch (IOException ioe) {
			LOGGER.error("IOException found in loadPropertyFileContents.  Error: ", ioe);
		} 

		return false;
	}
	
	private static InputStream setInputStreamFromFile(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}
	
	protected String getPropertyValue(String key) {
		if (properties == null) {
			throw new IllegalStateException("Properties object is null");
		}
		
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}
		
		return properties.getProperty(key);
	}
	
	// Used by unit tests
	protected void setProperties(Properties properties) {
		this.properties = properties;
	}
}
