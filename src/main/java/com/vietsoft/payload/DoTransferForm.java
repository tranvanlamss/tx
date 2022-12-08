package com.vietsoft.payload;

import java.util.List;

import lombok.Data;

@Data
public class DoTransferForm {

    private String fromDeliveryCode;
    private List<String> orderItemIds;
    private String toDeliveryCode;
}
