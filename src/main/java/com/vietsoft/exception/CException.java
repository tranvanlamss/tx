package com.vietsoft.exception;

import org.springframework.http.HttpStatus;

public class CException extends Exception {
	private static final long serialVersionUID = 6806734259943852026L;
	String code;
	HttpStatus status;

	public CException(HttpStatus status, String code, String message) {
		super(message);
		this.status = status;
		this.code = code;
	}

	public CException(HttpStatus status, String code, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
