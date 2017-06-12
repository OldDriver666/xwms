package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import com.fise.utils.JsonUtil;

public class QueryDeviceByAccountParam implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    @NotNull
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        
        return JsonUtil.toJson(this);
    }
    
    
}
