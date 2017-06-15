package com.fise.server.role;

import com.fise.base.Response;
import com.fise.model.param.RolePermissionParam;

public interface IRoleService {
	/*查询所有的角色记录*/
	public Response queryAll(Integer adminRole ,Integer orgId);
	
	/*查询角色并返回对应权限*/
	public Response queryRoleAuth(Integer adminRole ,Integer orgId);
	
	/*修改角色权限配置*/
	public Response updateRoleAuth(RolePermissionParam param);
}
