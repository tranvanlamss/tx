package com.vietsoft.payload.apiacl;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Usagelog {

	String Id;

	LocalDateTime createTime;

	String actionModel;

	String actionType;

	String actionName;

	String actionUserId;

	String actionUsername;

	String actionUserEmail;

	String content;

	String userAgent;

	String originalUrl;

	String refererUrl;

	String httpMethod;

	String httpParams;

	String httpStatus;

	String clientIp;

	String clientId;

	String outPut;

}
