package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fise.utils.JsonUtil;

public class QueryDeviceByMobileParam implements Serializable{
    private static final long serialVersionUID=1L;
    
    @NotNull
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        
        return JsonUtil.toJson(this);
    }
    
    
}
