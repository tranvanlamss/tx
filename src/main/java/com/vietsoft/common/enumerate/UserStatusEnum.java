package com.vietsoft.common.enumerate;

public enum UserStatusEnum {
	ACTIVATED("ACTIVATED"), DISACTIVATED("DISACTIVATED"), DELETED("DELETED"), PENDING("PENDING");

	String status;

	UserStatusEnum(String name) {
		this.status = name;
	}

	public int getValue() {
		UserStatusEnum l = getStatus(this.status);
		switch (l) {
		case DISACTIVATED:
			return 0;
		case ACTIVATED:
			return 1;
		case DELETED:
			return 2;
		case PENDING:
			return 3;
		}
		return 0;
	}
	
	public String getName() {
		return this.name();
	}

	public static UserStatusEnum getStatus(String name) {
		for (UserStatusEnum r : UserStatusEnum.values()) {
			if (r.name().equalsIgnoreCase(name)) {
				return r;
			}
		}
		return null;
	}

	public static String getDescription(UserStatusEnum name) {
		switch (name) {
		case ACTIVATED:
			return "Activated";
		case DISACTIVATED:
			return "Disactivated";
		case DELETED:
			return "Deleted";
		case PENDING:
			return "Pending";
		}
		return "";
	}
}
