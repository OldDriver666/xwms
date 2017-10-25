package com.fise.model.param;

import java.io.Serializable;
import java.util.List;

import com.fise.utils.JsonUtil;


public class InsertRoleAndAuthsParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 新增角色
     */
    private InsertRoleParam role ;
    
    /**
     * 角色分配的权限
     */
    private List<InsertAuthParam> auths;
    
    
	public InsertRoleParam getRole() {
		return role;
	}
	public void setRole(InsertRoleParam role) {
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
