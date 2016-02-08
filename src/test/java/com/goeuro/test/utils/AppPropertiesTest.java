package com.goeuro.test.utils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.goeuro.unit_test.utils.RandomUtil;

public class AppPropertiesTest {

	private static final String NON_EXISTENT_FILE_NAME = "non-existent.properties";
	private static final String NON_EXISTENT_KEY = "service.plan.connection.domain"; 

	private static AppProperties appProperties;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appProperties = AppProperties.getInstance();
	}
	
	@Test
	public void testWorkflowFactorySingletonHolderConstructorIsPrivate() throws Exception {
		Constructor<?> constructor = AppProperties.AppPropertiesSingletonHolder.class.getDeclaredConstructor();
	    assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	    constructor.setAccessible(true);
	    constructor.newInstance();	
	}

	@Test 
	public void testLoadPropertyFileContents_WhenPropertyFileValueIsEmtpy() {
		boolean expected = false;
		boolean actual = appProperties.loadPropertyFileContents("");
		assertEquals(expected, actual);
	}
	
	@Test 
	public void testLoadPropertyFileContents_WhenPropertyFileIsNotFound() {
		boolean expected = false;
		boolean actual = appProperties.loadPropertyFileContents(NON_EXISTENT_FILE_NAME);
		assertEquals(expected, actual);
	}

	@Test
	public void testLoadPropertyFileContents_WhenIOExceptionOccurs() throws IOException {
		// Set the SUT
		Properties mockProperties = mock(Properties.class);
		doNothing().doThrow(new IOException()).when(mockProperties).load(any(InputStream.class));
		appProperties.setProperties(mockProperties);

		// Run the test (Called twice to invoke the doNothing mock)
		boolean expected = false;
		appProperties.loadPropertyFileContents(AppProperties.PROPERTIES_FILE_NAME);
		boolean actual = appProperties.loadPropertyFileContents(AppProperties.PROPERTIES_FILE_NAME);
		assertEquals(expected, actual);
	}
	
	@Test (expected=IllegalStateException.class)
	public void testGetPropertyValue_WhenPropertiesObjectIsNull() {
		appProperties.setProperties(null);
		appProperties.getBaseUrl();
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetPropertyValue_WhenPropertyKeyIsNull() {
		appProperties.getPropertyValue(null);
	}
	
	@Test
	public void testGetPropertyValue_WhenUsingNonExistentKey() {
		String actual = appProperties.getPropertyValue(NON_EXISTENT_KEY);
		assertNull(actual);
	}
	
	@Test
	public void testGetBaseUrl_WhenKeyExists() {
		Properties properties = new Properties();
		String baseUrl = RandomUtil.getRandomUrl().toString();
		properties.setProperty(AppProperties.BASE_URL_KEY, baseUrl);
		appProperties.setProperties(properties);
		String actual = appProperties.getBaseUrl();
		assertEquals(baseUrl, actual);
	}
}
