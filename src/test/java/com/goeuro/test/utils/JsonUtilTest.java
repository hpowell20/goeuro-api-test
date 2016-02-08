package com.goeuro.test.utils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

public class JsonUtilTest {

	@Test
    public void testConstructorIsPrivate() throws Exception {
      Constructor<?> constructor = JsonUtil.class.getDeclaredConstructor();
      assertTrue(Modifier.isPrivate(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    }

	@Test(expected=IllegalArgumentException.class)
	public void testGetJsonArrayFromResponse_WhenResponseIsNull() {
		JsonUtil.getJsonArrayFromResponse(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetJsonArrayFromResponse_WhenResponseIsEmpty() {
		JsonUtil.getJsonArrayFromResponse("");
	}
	
	@Test(expected=JSONException.class)
	public void testGetJsonArrayFromResponse_WhenResponseIsNotArray() {
		String jsonResponse = "{\"name\":\"unit\",\"value\":\"test\"}";
		JsonUtil.getJsonArrayFromResponse(jsonResponse);
	}
	
	@Test
	public void testGetJsonArrayFromResponse_WhenResponseIsArray() {
		String jsonResponse = "[{\"name\":\"unit\",\"value\":\"test\"}]";
		JSONArray actual = JsonUtil.getJsonArrayFromResponse(jsonResponse);
		assertNotNull(actual);
		assertThat(actual.length(), equalTo(1));
	}
}
