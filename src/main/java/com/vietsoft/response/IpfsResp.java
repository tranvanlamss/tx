package com.vietsoft.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@SuppressWarnings("serial")
public class IpfsResp implements Serializable {

	@JsonProperty("Name")
	String name;
	
	@JsonProperty("Hash")
	String hash;
	
	@JsonProperty("Size")
	long size;
	
	@JsonProperty("Message")
	String message;
	
	@JsonProperty("Type")
	String type;
	
	@JsonProperty("Code")
	long code;

	public String getName() {
		return name;
	}

	public String getHash() {
		return hash;
	}

	public long getSize() {
		return size;
	}

	public String getMessage() {
		return message;
	}

	public String getType() {
		return type;
	}

	public long getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(long code) {
		this.code = code;
	}
}