package com.goeuro.test.utils;

import org.json.JSONArray;

import com.google.common.base.Strings;

public final class JsonUtil {

	// Private constructor to prevent instantiation
	private JsonUtil() {
		
	}
	
	public static JSONArray getJsonArrayFromResponse(String jsonResponse) {
		if (Strings.isNullOrEmpty(jsonResponse)) {
			throw new IllegalArgumentException("jsonResponse");
		}
		
		return new JSONArray(jsonResponse);
	}
}
