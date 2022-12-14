package com.vietsoft.common;

import com.vietsoft.model.User;
import com.vietsoft.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class UserUtil {

	static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

	public static Authentication getAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}

	public static UserPrincipal getPrincipal() {
		Authentication auth = getAuth();
		if (auth != null) {
			Object u = auth.getPrincipal();
			if (u instanceof UserPrincipal || u instanceof User) {
				return (UserPrincipal) auth.getPrincipal();
			}
		}
		return null;
	}

	public static String getUserId() {
		User u = getCurrentUser();
		if (u != null) {
			return u.getId();
		}
		return "";
	}

	public static String getUserName() {
		User u = getCurrentUser();
		if (u != null) {
			return u.getUsername();
		}
		return "";
	}
	public static String getName() {
		User u = getCurrentUser();
		String name = "";
		if (u != null) {
			if (!StringUtils.isEmpty(u.getFullName())){
				name = u.getFullName();
			} else {
				name = u.getShortName();
			}
		}
		return name;
	}

	public static List<String> getUserRoles() {
		Authentication au = getAuth();
		if (au == null)
			return null;

		return au.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList());
	}
	
	public static User getCurrentUser() {
		return getPrincipal();
	}
}
