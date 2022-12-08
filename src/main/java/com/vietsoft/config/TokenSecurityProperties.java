package com.vietsoft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "security.token")
public class TokenSecurityProperties {

	String secret;
	int size;
	int expiry;
	boolean enableCookie;
	int pwSalt;
	public String getSecret() {
		return secret;
	}
	public int getSize() {
		return size;
	}
	public int getExpiry() {
		return expiry;
	}
	public boolean isEnableCookie() {
		return enableCookie;
	}
	public int getPwSalt() {
		return pwSalt;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
	public void setEnableCookie(boolean enableCookie) {
		this.enableCookie = enableCookie;
	}
	public void setPwSalt(int pwSalt) {
		this.pwSalt = pwSalt;
	}

}
