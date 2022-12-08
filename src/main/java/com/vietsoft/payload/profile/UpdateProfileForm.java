package com.vietsoft.payload.profile;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.vietsoft.model.Media;

public class UpdateProfileForm {

	String name;

	String address;

	@Email(message = "Invalid email address")
	String email;
	@Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
	String phone;

	String province;

	List<Media> media;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Media> getMedia() {
		return media;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
