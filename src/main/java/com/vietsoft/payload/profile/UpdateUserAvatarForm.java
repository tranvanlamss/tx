package com.vietsoft.payload.profile;

import org.springframework.web.multipart.MultipartFile;

public class UpdateUserAvatarForm {
	private MultipartFile image;
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
