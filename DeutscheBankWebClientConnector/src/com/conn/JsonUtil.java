package com.conn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
	}

	public static String converJavaToString(Object obj) {
		String jsonData = "";
		try {
			jsonData = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonData;
	}
}
