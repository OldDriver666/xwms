package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author bension
 * @email liaoguoshun@qq.com
 * @date 2017-6-2
 * @desc 查询模块参数实体类
 */

public class ModuleQueryParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@JsonProperty("admin_id")
	private Integer adminId;

	@NotNull
    @JsonProperty("role_id")
    private Integer roleId;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
