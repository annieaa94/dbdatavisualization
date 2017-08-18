package com.conn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {
	private static ObjectMapper mapper;
	//private static ObjectWriter writer;

	static {
		mapper = new ObjectMapper();
		//writer = mapper.writer();
	}

	public static String converJavaToString(Object obj) {
		//System.out.println(obj);
		String jsonData = "";
		try {
			jsonData = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(jsonData);
		return jsonData;
	}
}
