package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class DeviceCountParam implements Serializable{
    private static final long serialVersionUid=1L;
    
    @NotNull
    @JsonProperty("company_id")
    private Integer companyId;
    
    @JsonProperty("depart_id")
    private Integer departid;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
