package com.vietsoft.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CustomerForm {

	String parentId;// ID of agent Distributor or Agency

	String ownerId;// ID of account (user ID) Login user

	String code;

	String shortName;

	String name;
	
	String bizRole;

	String status;

	String province;

	String address;

	String shopType;

	String area;

	@Email(message = "Invalid email address")
	String email;
	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
	String fax;
	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
	String hostline;
	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
	String phone;
	
	String description;
}
