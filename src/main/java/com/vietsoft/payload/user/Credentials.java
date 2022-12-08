package com.vietsoft.payload.user;

import javax.validation.constraints.NotBlank;

public class Credentials {
	@NotBlank(message="User ID can not be blank")
	String email;
	
	@NotBlank(message="Password can not be blank")
  String password;

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
