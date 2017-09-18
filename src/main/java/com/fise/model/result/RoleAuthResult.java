package com.fise.model.result;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.model.entity.WiPermission;
import com.fise.utils.JsonUtil;

/** 
 * @author benssion
 * @email liaoguoshun@qq.com
 * @date 2017-6-13
 * @desc 角色权限
 */

public class RoleAuthResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("role_id")
	private Integer roleId;
	
	@JsonProperty("role_name")
	private String roleName;

	@JsonProperty("auth_list")
	private List<WiPermission> authList;
	
	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<WiPermission> getAuthList() {
        return authList;
    }

    public void setAuthList(List<WiPermission> authList) {
        this.authList = authList;
    }

    @Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
