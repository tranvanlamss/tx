package com.vietsoft.payload.user;

import javax.validation.constraints.NotBlank;

public class ChangePassword {
	
	@NotBlank(message="Password can not be empty")
	String password;
	
	@NotBlank(message="Password can not be empty")
	String newPassword;
	
	@NotBlank(message="User ID can not be empty")
	String userId;

	public String getPassword() {
		return password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
