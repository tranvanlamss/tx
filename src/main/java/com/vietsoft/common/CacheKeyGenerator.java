package com.vietsoft.common;

import java.lang.reflect.Method;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;

public class CacheKeyGenerator implements KeyGenerator {

	static final Logger logger = LoggerFactory.getLogger(CacheKeyGenerator.class);

	@Override
	public Object generate(Object target, Method method, Object... params) {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		logger.info("Generate a cache ID: " + id);
		return id;
	}

}
