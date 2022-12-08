package com.vietsoft.payload.user;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrgProfile {
	String id;
	
//	@Size(max = 24, message="ORG ID allows max 24 characters")
//	String orgId;

	@Size(max = 64, message="Business Number allows max 64 characters")
	String businessNumber;

	//@Size(max = 256, message="Position allows max 256 characters")
	//String codePrefix;

	@Size(max = 1024, message="Description allows max 1024 characters")
	String description;

	@Email(message="Email must be valid one")
	String email;

	@Past(message="Established Date must be in past")
	Date establishedDate;

	@Pattern(regexp="(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message="Invalid phone number")
	String fax;

	@Pattern(regexp="(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message="Invalid phone number")
	String hostline;

	@Pattern(regexp="(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message="Invalid phone number")
	String phone;

	@Size(max = 256, message="Name allows max 256 characters")
	String name;

	@Size(max = 256, message="Representative Name allows max 256 characters")
	String representativeName;

	@Size(max = 256, message="Representative Position allows max 256 characters")
	String representativePosition;

	@Size(max = 32, message="Short Name allows max 32 characters")
	String shortName;

	@Size(max = 64, message="Tax Number allows max 64 characters")
	String taxNumber;

	@Size(max = 256, message="Website allows max 256 characters")
	String websiteUrl;
	
	@Size(max = 3, message="Country Code allows max 64 characters")
	String countryCode;

	@Size(max = 256, message="Country Name allows max 256 characters")
	String country;
	
	@Size(max = 256, message="Address allows max 256 characters")
	String address;

	
	@Size(max = 256, message="Province allows max 256 characters")
	String province;
	
	@NotBlank
	List<String> bizRoles;
}