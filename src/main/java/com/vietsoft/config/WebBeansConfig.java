package com.vietsoft.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vietsoft.security.LocalTokenAuthFilter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebBeansConfig {
	static final Logger logger = LoggerFactory.getLogger(WebBeansConfig.class);
	
	public static final int PW_SALT = 12;
	public static final String PW_SALT_TOKEN = String.format("$2a$%s$", PW_SALT);
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(PW_SALT);
	}
	
	@Bean
    public LocalTokenAuthFilter localTokenAuthFilter() {
        return new LocalTokenAuthFilter();
    }
	
	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
	    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
	}
	
}