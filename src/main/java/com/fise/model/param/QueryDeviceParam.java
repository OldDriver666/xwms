package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class QueryDeviceParam implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    private String account;
    
    @NotNull
    private Integer type;
    
    @JsonProperty("device_id")
    private Integer deviceId;
    
    private String phone;
    
    @JsonProperty("depart_id")
    @NotNull
    private Integer departid;
    
    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {       
        return JsonUtil.toJson(this);
    }
      
}
