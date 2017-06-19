package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

public class GroupMessageQueryParam implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonProperty("admin_id")
    @NotNull
    private Integer adminId;
    
    @NotEmpty
    @MaxLength(value = 50)
    private String name;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
