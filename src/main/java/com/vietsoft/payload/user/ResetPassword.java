package com.vietsoft.payload.user;

import javax.validation.constraints.NotBlank;

public class ResetPassword {
	
	@NotBlank(message="User Password can not blank")
	String password;
	
	@NotBlank(message="User ID can not blank")
	String userId;

	public String getPassword() {
		return password;
	}

	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
