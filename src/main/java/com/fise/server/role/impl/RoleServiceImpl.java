package com.fise.server.role.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.WiOrganizationRoleMapper;
import com.fise.dao.WiPermissionMapper;
import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.entity.WiOrganizationRoleExample;
import com.fise.model.entity.WiPermission;
import com.fise.model.entity.WiPermissionExample;
import com.fise.model.param.ModuleQueryResult;
import com.fise.model.param.RolePermissionParam;
import com.fise.model.result.RoleAuthResult;
import com.fise.server.role.IRoleService;
import com.fise.utils.DateUtil;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    WiOrganizationRoleMapper roleDao;
    
    @Autowired
    private WiPermissionMapper permissionDao;
    
    @Override
    public Response queryAll(Integer adminRole, Integer orgId) {
        /*TODO 根据组织ID查询角色*/
        Response resp = new Response();
        //预先查询管理者权限
        WiOrganizationRole admin = roleDao.selectByPrimaryKey(adminRole);
        WiOrganizationRoleExample example = new WiOrganizationRoleExample();
        WiOrganizationRoleExample.Criteria criterion = example.createCriteria();
        criterion.andAuthLevelLessThanOrEqualTo(admin.getAuthLevel());
        //目前只有一个公司角色记录,所以默认返回所有公用
        List<WiOrganizationRole> roleList = roleDao.selectByExample(example);
        resp.success(roleList);
        return resp;
    }

    @Override
    public Response queryRoleAuth(Integer adminRole ,Integer orgId) {
        /*TODO 根据组织ID查询角色*/
        Response resp = new Response();
        //需要返回的真实JSON格式数据
        List<RoleAuthResult> data = new ArrayList<RoleAuthResult>();
        
        WiOrganizationRole admin = roleDao.selectByPrimaryKey(adminRole);
        WiOrganizationRoleExample example = new WiOrganizationRoleExample();
        WiOrganizationRoleExample.Criteria criterion = example.createCriteria();
        criterion.andAuthLevelLessThanOrEqualTo(admin.getAuthLevel());
        List<WiOrganizationRole> roleList = roleDao.selectByExample(example);
        for(WiOrganizationRole role : roleList){
            //查询子角色权限
            List<ModuleQueryResult> tmpResult = new ArrayList<ModuleQueryResult>();
            tmpResult = permissionDao.selectPermissionByRole(role.getId());
            //赋值对应角色权限
            RoleAuthResult tmpData = new RoleAuthResult();
            tmpData.setRoleId(role.getId());
            tmpData.setRoleName(role.getName());
            tmpData.setAuthList(tmpResult);
            data.add(tmpData);
        }
        resp.success(data);
        return resp;
    }

    @Override
    public Response updateRoleAuth(RolePermissionParam param) {
        Response resp = new Response();
        WiPermissionExample example = new WiPermissionExample();
        List<RolePermissionParam.Permission> updBox =  param.getPermisList();
        for(RolePermissionParam.Permission tmp : updBox){
            example.clear();
            WiPermission dbValue = new WiPermission();
            if(tmp.getInsertAuth() != null){
                dbValue.setInsertAuth(tmp.getInsertAuth());
            }
            if(tmp.getQueryAuth() != null){
                dbValue.setQueryAuth(tmp.getQueryAuth());
            }
            if(tmp.getUpdateAuth() != null){
                dbValue.setUpdateAuth(tmp.getUpdateAuth());
            }
            
            if(tmp.getId() == null){
                //for insert
                dbValue.setRoleId(param.getRoleId());
                dbValue.setCreated(DateUtil.getLinuxTimeStamp());
                dbValue.setUpdated(DateUtil.getLinuxTimeStamp());
                dbValue.setStatus(tmp.getStatus());
                dbValue.setModuleId(tmp.getModuleId());
                permissionDao.insertSelective(dbValue);
            } else {
                //for update
                dbValue.setId(tmp.getId());
                dbValue.setUpdated(DateUtil.getLinuxTimeStamp());
                if(tmp.getStatus() != null){
                    dbValue.setStatus(tmp.getStatus());
                }
                permissionDao.updateByPrimaryKeySelective(dbValue);
            }
        }
        resp.success();
        return resp;
    }

}
