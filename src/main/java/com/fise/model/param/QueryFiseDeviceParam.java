package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class QueryFiseDeviceParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("fise_id")
    private Integer fiseId;

    private String ime;

    private String account;

    @JsonProperty("depart_id")
    private Integer departid;

    @NotNull
    @JsonProperty("company_id")
    private Integer companyId;

    private Integer status;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    public Integer getFiseId() {
        return fiseId;
    }

    public void setFiseId(Integer fiseId) {
        this.fiseId = fiseId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

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
