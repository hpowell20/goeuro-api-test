package com.goeuro.unit_test.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

import com.google.common.base.Strings;

public final class RandomUtil {
	private static String validChars = null;
	private static SecureRandom random = new SecureRandom();

	// Private constructor to prevent instantiation
	private RandomUtil() {
		
	}
	
	private static String getValidChars() {
		if (Strings.isNullOrEmpty(validChars)) {
			generateValidCharsString();
		}

		return validChars;
	}

	private static void generateValidCharsString() {
		for (int i = 0x61; i < 0x7B; i++) {
			validChars += Character.toString((char)i);
		}
	}

	public static String getRandomString() {
		return getRandomString(random.nextInt(100) + 1);
	}

	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);

		String charsToUse = getValidChars();
		for (int i = 0; i < length; i++) {
			sb.append(charsToUse.charAt(random.nextInt(charsToUse.length())));
		}

		return sb.toString();
	}

	public static int getRandomInt() {
		return random.nextInt();
	}

	public static int getRandomInt(int maxRange) {
		return random.nextInt(maxRange);
	}

	public static int getRandomInt(int min, int max) {
        // The range of numbers is inclusive to min and max.
        return random.nextInt((max - min) + 1) + min;
    }
	
	public static Long getRandomLong() {
		return random.nextLong();
	}
	
	public static double getRandomDouble() {
		return random.nextDouble();
	}

	public static boolean getRandomBoolean() {
		return random.nextBoolean();
	}
	
	public static URL getRandomUrl() {
		return getRandomUrl(getRandomBoolean());
	}
	
	protected static URL getRandomUrl(boolean useSsl) {
		StringBuilder sbuf = new StringBuilder();
        sbuf.append(useSsl ? "https" : "http");
        sbuf.append("://www.");
        sbuf.append(RandomUtil.getRandomString());
        sbuf.append(".com");

        return setUrlFromString(sbuf.toString());
	}

	protected static URL setUrlFromString(String urlString) {
		try {
        	return new URL(urlString);
        } catch(MalformedURLException e) {
        	return null;
        }
	}
}
