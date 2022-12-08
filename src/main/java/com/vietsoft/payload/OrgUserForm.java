package com.vietsoft.payload;

import javax.validation.constraints.NotNull;

import com.vietsoft.payload.user.OrgProfile;
import com.vietsoft.payload.user.UserProfile;

import lombok.Data;

@Data
public class OrgUserForm {
	@NotNull
	UserProfile user;
	@NotNull
	OrgProfile org;
}
