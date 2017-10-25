package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;


public class InsertAuthParam implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("key_id")
    private Integer permissionId;

    @NotNull
    @JsonProperty("role_id")
    private Integer roleId;
    
    @JsonProperty("module_id")
    private Integer moduleId;

    @JsonProperty("company_id")
    private Integer companyId;

    private Integer status;
    
    @JsonProperty("insert_auth")
    private Integer insertAuth;

    @JsonProperty("update_auth")
    private Integer updateAuth;

    @JsonProperty("query_auth")
    private Integer queryAuth;

    public Integer getPermissionId() {
		return permissionId;
	}


	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
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


    public Integer getRoleId() {
        return roleId;
    }


    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public Integer getModuleId() {
        return moduleId;
    }


    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }


    public Integer getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
