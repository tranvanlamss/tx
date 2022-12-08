package com.vietsoft.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vietsoft.common.CacheKeyGenerator;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {
	// Simple caching
//	@Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(Arrays.asList(
//        		new ConcurrentMapCache("UserPrincipal"),
//        		new ConcurrentMapCache("AccessToken")
//        ));
//        return cacheManager;
//    }
	
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new CacheKeyGenerator();
	}
}
