package com.vietsoft.payload.viewlogs;

import java.util.Date;

public class ViewLogsForm {

    private String salCode;

    private String productionId;

    private Date createTime;

    private String browserAgent;

    private String createrId;

    public String getSalCode() {
        return salCode;
    }

    public void setSalCode(String salCode) {
        this.salCode = salCode;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBrowserAgent() {
        return browserAgent;
    }

    public void setBrowserAgent(String browserAgent) {
        this.browserAgent = browserAgent;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }
}
