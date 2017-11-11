package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class QueryUserParam implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    private String domain;
    
    private String phone;

    @JsonProperty("user_id")
    private Integer userId;
    
    @JsonProperty("depart_id")
    private Integer departId;
    
    @JsonProperty("company_id")
    private Integer companyId;
    
    @JsonProperty("query_date")
    private String queryDate;
    
    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    @JsonProperty("online_status")
    private Integer onlineStatus;
    
    
    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
