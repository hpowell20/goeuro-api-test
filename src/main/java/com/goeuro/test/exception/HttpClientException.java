package com.goeuro.test.exception;

public class HttpClientException extends Exception {

	private static final long serialVersionUID = 4641051704584810673L;

	public HttpClientException(Exception e) {
		super(e);
	}

	public HttpClientException(String message) {
		super(message);
	}
}
