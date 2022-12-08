package com.vietsoft.payload.profile;

import java.util.List;

import com.vietsoft.model.Role;

public class UpdateInformation {
	private String id;
	
	private String username;
	
	private String password;
	
	private String fullName;
	private String shortName;
	private String phone;

	private String email;
	
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public String getFirstName() {
		String[] parts = fullName.split(" ");
		return parts[0];
	}

	public String getLastName() {
		String[] parts = fullName.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0;i < parts.length - 1;i++) {
			sb.append(parts[i] + " ");
		}
		return sb.append(parts[parts.length - 1]).toString();
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
