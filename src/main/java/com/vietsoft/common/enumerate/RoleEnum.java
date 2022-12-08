package com.vietsoft.common.enumerate;

import java.util.Map;

public enum RoleEnum {
	ADMIN("Administrator"),
	USER("User"),
	SHOP("Shop");

	private static Map<String, RoleEnum> map;

	private final String role;

	public String getRole() {
		return role;
	}

	RoleEnum(final String role) {
		this.role = role;
	}

	public static RoleEnum getRole(String name) {
		if(map != null) {
			return map.get(name);
		}
		for (RoleEnum r : RoleEnum.values()) {
			map.put(r.name(), r);
		}
		return map.get(name);
	}
}
