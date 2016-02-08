package com.goeuro.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private Main() {
		
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			LOGGER.error("No city name entered; please add one to continue");
			return;
		}
		
		ProcessRequest processRequest = new ProcessRequest();
		processRequest.processGoEuroRequest(args[0]);
	}
}
