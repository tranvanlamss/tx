package com.vietsoft.payload.user;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserProfile {
	String id;

	@Size(max = 24, message="ORG ID allows max 24 characters")
	String orgId;

	@Size(max = 128, message = "User Name allows max 128 characters")
	String username;

	@Email(message = "Invalid Email Address")
	String email;
	@Size(max = 128, message = "First Name allows max 128 characters")
	String firstName;
	@Size(max = 128, message = "Last Name allows max 128 characters")
	String lastName;
	@Size(max = 256, message = "Full Name allows max 256 characters")
	String fullName;
	@Size(max = 32, message = "Short Name allows max 32 characters")
	String shortName;

	String avatarUrl;

	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
	String phone;

	int status;
	
	@Past(message = "Invalid Birthday Date")
	Date birthday;
	
	String position;
}
