package com.vietsoft.payload.user;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MemberUser {
	String id;

	@NotBlank(message = "Member Email can not be blank")
	@Email(message = "Invalid email address")
	String email;

	@NotBlank(message = "Member Name can not be blank")
	String fullName;
	@NotBlank(message = "Member Name can not be blank")
	String shortName;
	
	@NotBlank(message = "Member Biz Role can not be blank")
	String bizRole;
	
	@NotBlank(message = "Member Role can not be blank")
	String role;

	Integer status = 1;

	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
	String phone;

	// @NotBlank(message="Password can not be blank")
	String password;

	@Past(message = "Birthday Date must be in past")
	Date birthday;


}
