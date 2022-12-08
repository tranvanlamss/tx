package com.vietsoft.payload.user;

import javax.validation.constraints.NotBlank;

public class UserBizType {
	
	@NotBlank(message="UID can not be blank")
	String uid;
	
	
	@NotBlank(message="Type can not be blank")
	String bizType;


	public String getUid() {
		return uid;
	}


	public String getBizType() {
		return bizType;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
}
