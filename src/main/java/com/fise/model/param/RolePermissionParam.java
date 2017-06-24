package com.fise.model.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author bension
 * @email liaoguoshun@qq.com
 * @date 2017-6-13
 * @desc 权限新增增删改查接口参数
 */

public class RolePermissionParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("role_id")
	@NotNull
	private Integer roleId;
	
	public static class Permission{
	    @JsonProperty("permission_id")
	    private Integer id;
	    
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
	
	@JsonProperty("permis_list")
	private List<Permission> permisList;

	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Permission> getPermisList() {
        return permisList;
    }

    public void setPermisList(List<Permission> permisList) {
        this.permisList = permisList;
    }

    @Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
