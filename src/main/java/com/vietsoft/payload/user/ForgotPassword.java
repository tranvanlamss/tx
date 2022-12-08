package com.vietsoft.payload.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ForgotPassword {
	
	@NotBlank(message="Email can not be blank")
	@Email(message="Invalid email address")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
