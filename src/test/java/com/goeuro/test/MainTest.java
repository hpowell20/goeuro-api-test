package com.goeuro.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainTest {
	
	@Mock
	private ProcessRequest mockProcessRequest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		try {
			Field field = Main.class.getDeclaredField("processRequest");
			field.setAccessible(true);
			field.set(null, mockProcessRequest);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void testConstructorIsPrivate() throws Exception {
      Constructor<?> constructor = Main.class.getDeclaredConstructor();
      assertTrue(Modifier.isPrivate(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    }
	
	@Test
	public void testMain_WhenArgumentListIsEmpty() {
		Main.main(new String[] {});
		
		// Verify mock interactions
		verifyZeroInteractions(mockProcessRequest);
	}

	@Test
	public void testMain_WhenArgumentListIsNotEmpty() {
		// Setup the SUT
		when(mockProcessRequest.processGoEuroRequest(anyString())).thenReturn(true);
		
		Main.main(new String[] {"Berlin"});
		
		// Verify mock interactions
		verify(mockProcessRequest, times(1)).processGoEuroRequest(anyString());
	}
}
