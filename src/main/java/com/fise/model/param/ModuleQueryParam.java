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
    @JsonProperty("auth_level")
    private Integer authLevel;
	
	@NotNull
	@JsonProperty("company_id")
	private Integer companyId;
	
	
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }


    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
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
