package com.fise.model.param;

import java.io.Serializable;
import java.util.List;

import com.fise.model.entity.WiOrganizationRole;
import com.fise.utils.JsonUtil;


public class UpdateRoleAndAuthsParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 修改角色
     */
    private WiOrganizationRole role ;
    
    /**
     * 角色分配的权限
     */
    private List<InsertAuthParam> auths;
    
    

	public WiOrganizationRole getRole() {
		return role;
	}
	public void setRole(WiOrganizationRole role) {
		this.role = role;
	}
	public List<InsertAuthParam> getAuths() {
		return auths;
	}
	public void setAuths(List<InsertAuthParam> auths) {
		this.auths = auths;
	}
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

}
