package com.vietsoft.payload.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SetRole {
	
	String curRole;
	
	@NotBlank(message="New Role can not be blank")
	String newRole;
	
	@NotBlank(message="User ID can not be blank")
	String userId;
	
	@NotBlank(message="Biz Role can not be blank")
	String bizRole;
}
