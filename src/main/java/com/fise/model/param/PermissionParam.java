package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

class Permission implements Serializable {
    @JsonProperty("permission_id")
    private Integer id;
    
    @NotNull
    @JsonProperty("module_id")
    private Integer moduleId;
    
    @JsonProperty("status")
    private Integer status;
    
    @JsonProperty("insert_auth")
    private Integer insertAuth;
    
    @JsonProperty("update_auth")
    private Integer updateAuth;
    
    @JsonProperty("query_auth")
    private Integer queryAuth;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInsertAuth() {
        return insertAuth;
    }

    public void setInsertAuth(Integer insertAuth) {
        this.insertAuth = insertAuth;
    }

    public Integer getUpdateAuth() {
        return updateAuth;
    }

    public void setUpdateAuth(Integer updateAuth) {
        this.updateAuth = updateAuth;
    }

    public Integer getQueryAuth() {
        return queryAuth;
    }

    public void setQueryAuth(Integer queryAuth) {
        this.queryAuth = queryAuth;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}

