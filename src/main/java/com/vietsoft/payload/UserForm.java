package com.vietsoft.payload;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserForm {
    String role;
    String username;
    String email;
    String name;

    @Pattern(regexp = "(^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\\s\\./0-9]*$)|([\\s]*)", message = "Invalid phone number")
    String phone;

    String shortName;

    String plantCode;
    
    String bizRole;
}
