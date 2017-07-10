package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class ElectricFenceParam implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    @JsonProperty("fence_id")
    private Integer fenceId;
    
    private String name;

    public Integer getFenceId() {
        return fenceId;
    }

    public void setFenceId(Integer fenceId) {
        this.fenceId = fenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
    
    
}
