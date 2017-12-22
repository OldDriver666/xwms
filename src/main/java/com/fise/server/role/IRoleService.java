package com.fise.server.role;

import java.util.List;



import com.fise.base.Response;
import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.param.InsertAuthParam;
import com.fise.model.param.InsertRoleParam;
import com.fise.model.param.QueryRoleParam;
import com.fise.model.param.RolePermissionParam;
import com.fise.model.result.ModulePermissResult;

public interface IRoleService {
    /* 查询所有的角色记录 */
    public List<WiOrganizationRole> queryRole(QueryRoleParam param);

    /* 查询角色并返回对应权限-不包括不可见的 */
    public Response queryRoleAuth(QueryRoleParam param);

    //更新角色信息
    public Response updateRole(WiOrganizationRole param);
    /* 修改角色权限配置 */
    public Response updateRoleAuth(RolePermissionParam param);

    /* 新增角色 */
    public Response insertRole(InsertRoleParam role);
    public Response insertAuth(InsertAuthParam role);

    public Response delRole(WiOrganizationRole role);
    
    /**
     * 新增角色和权限
     */
    public Response insertRoleAndAuths(InsertRoleParam role, List<InsertAuthParam> auths);
    
    
    /**
     * 修改角色和权限
     */
    public Response updateRoleAndAuths(WiOrganizationRole param, List<InsertAuthParam> auths);
    
    
    /**
     * 删除角色和权限
     */
    public Response deleteRoleAndAuths(InsertAuthParam role);

    /**
     * 删除权限
     */
	public Response delAuth(InsertAuthParam auth);
	

	
    
    
}
