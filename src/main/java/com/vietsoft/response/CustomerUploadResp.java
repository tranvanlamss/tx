package com.vietsoft.response;

import java.util.List;

import lombok.Data;

@Data
public class CustomerUploadResp {
    private String name;
    private String shortName;
    private String email;
    private String phone;
    private String address;
    private String province;
    private String code;
    private String role;
    private String shopType;
    private String area;
    private List<String> parentCodes;
    private boolean emailIsValidFormat;
    private boolean isEmailDuplicate;
    private boolean isCodeDuplicate;
    private boolean emailIsExisted;
    private boolean codeIsExisted;
}
