package com.vietsoft.payload.customer;

import java.util.List;

import com.vietsoft.response.CustomerUploadResp;

public class ListCustomerJson {
    private List<CustomerUploadResp> list;

    public List<CustomerUploadResp> getList() {
        return list;
    }

    public void setList(List<CustomerUploadResp> list) {
        this.list = list;
    }
}
