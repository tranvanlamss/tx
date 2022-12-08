package com.vietsoft.payload.user;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterUser {
	String captkey;
	String captcha;
	
	@NotBlank(message="username can not be blank")
	String username;
	
	@NotBlank(message="Email can not be blank")
	@Email(message="Invalid email address")
	String email;
	
	@Pattern(regexp="(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message="Invalid phone number")
	String phoneNumber;
	
	@NotBlank(message="Your Name can not be blank")
	String yourName;

	String shortName;

	@NotBlank(message="Password can not be blank")
	String password;

	@NotBlank(message="Your Name can not be blank")
	String companyName;

	String companyShortName;
	
	@NotBlank(message="Your Name can not be blank")
	String companyAddress;

	@NotBlank(message="Your Name can not be blank")
	@Pattern(regexp="(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message="Invalid phone number")
	String companyPhone;
	
	@ApiModelProperty(value = "bizRole", example = "PRODUCER | VERIFIER | TRANSPORTER | RETAILER")
	@NotBlank(message="Biz role can not be blank")
	String bizRole;
}
