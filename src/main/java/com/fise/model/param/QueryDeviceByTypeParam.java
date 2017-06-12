package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fise.utils.JsonUtil;

public class QueryDeviceByTypeParam implements Serializable{
    private static final long serialVersionUID=1L;
    
    @NotNull
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
     
        return JsonUtil.toJson(this);
    }
    
    
}
