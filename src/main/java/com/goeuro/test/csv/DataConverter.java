package com.goeuro.test.csv;

import java.util.List;

public interface DataConverter<T> {
	String[] getHeaders();
	List<String> toRecord(T obj);
}
