package com.vietsoft.payload.customer;

import org.springframework.web.multipart.MultipartFile;

import com.vietsoft.payload.CustomerForm;
public class CustomerFormWrapper extends CustomerForm{
    private MultipartFile image;
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
