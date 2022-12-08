package com.vietsoft.payload.user;

import javax.validation.constraints.NotBlank;

public class VerifyEmail {
	
	@NotBlank(message="UID can not be blank")
	String uid;
	
	
	@NotBlank(message="Token Name can not be blank")
	String token;


	public String getUid() {
		return uid;
	}


	public String getToken() {
		return token;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
}
