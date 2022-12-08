package com.vietsoft.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
	static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	public static String toString(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(object).toString();
		} catch (JsonProcessingException e) {
			logger.info("JsonUtil---> Exception: {}", e.getMessage());
			json = "";
		}
		return json;
	}
    
	public static <S extends Object> S from(byte[] json, final Class<S> clazz) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		S obj = null;
		try {
			obj = objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw e;
		}
		return obj;
	}
}
