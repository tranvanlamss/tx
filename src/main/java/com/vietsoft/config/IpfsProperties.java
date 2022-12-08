package com.vietsoft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "ipfs.properties")
public class IpfsProperties {

	String scheme;
	String host;
	int port;
	String path;
	public String getScheme() {
		return scheme;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getPath() {
		return path;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
