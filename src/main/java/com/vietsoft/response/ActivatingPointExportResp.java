package com.vietsoft.response;

import java.util.List;

import lombok.Data;

@Data
public class ActivatingPointExportResp {

    private String name;
    private String address;
    private String phone;
    private String email;
    private String actType;
    private String province;
    private int sumProduct;
    private int sumPoint;
    private String shopCode;
    private List<String> productionCode;
    private List<String> createdTime;
    private String activationDate;

}
