package com.fise.server.module.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.WiModuleMapper;
import com.fise.dao.WiPermissionMapper;
import com.fise.model.entity.WiModule;
import com.fise.model.entity.WiModuleExample;
import com.fise.model.param.ModuleInsertParam;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleQueryResult;
import com.fise.model.param.ModuleUpdateParam;
import com.fise.server.module.IModuleService;
import com.fise.utils.StringUtil;

@Service
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private WiModuleMapper moduleDao;
    
    @Autowired
    private WiPermissionMapper permissionDao;
    
    @Override
    public Response QueryModule(ModuleQueryParam param) {
        Response resp = new Response();
        /*TODO 默认认为请求者传的roleId有效可用，不做检测 根据情况定是否检测值有效性*/
        List<ModuleQueryResult> returnData = new ArrayList<ModuleQueryResult>();
        List<ModuleQueryResult> data = permissionDao.selectPermissionByRole(param.getRoleId());
        for(ModuleQueryResult tmpData : data){
            if(tmpData.getStatus() != 0){
                returnData.add(tmpData);
            }
        }
        resp.success(returnData);
        return resp;
    }

    @Override
    public Response InsertModule(ModuleInsertParam param) {
        Response resp = new Response();
        /*TODO 做角色权限判断*/
        WiModule module = new WiModule();
        module.setName(param.getName());
        module.setParentId(param.getParentId());
        module.setPriority(param.getPriority());
        if(param.getDescription() != null){
            module.setDescription(param.getDescription());
        }
        if(param.getUrl() != null){
            module.setUrl(param.getUrl());
        }
        module.setSn(param.getSn());
        module.setStatus(param.getStatus());
        moduleDao.insert(module);
        resp.success();
        return resp;
    }

    @Override
    public Response UpdateModule(ModuleUpdateParam param) {
        /*TODO 做角色权限判断*/
        Response resp = new Response();
        WiModule module = new WiModule();
        module.setId(param.getModuleId());
        if(!StringUtil.isEmpty(param.getName())){
            module.setName(param.getName());
        }
        if(param.getParentId() != null){
            module.setParentId(param.getParentId());
        }
        if(param.getPriority() != null){
            module.setPriority(param.getPriority());
        }
        if(param.getDescription() != null){
            module.setDescription(param.getDescription());
        }
        if(param.getSn() != null){
            module.setSn(param.getSn());
        }
        if(param.getStatus() != null){
            module.setStatus(param.getStatus());
        }
        if(param.getUrl() != null){
            module.setUrl(param.getUrl());
        }
        moduleDao.updateByPrimaryKeySelective(module);
        resp.success();
        return resp;
    }

    @Override
    public Response QueryModuleAll(ModuleQueryParam param) {
        Response resp = new Response();
        // TODO  检测用户是否有权查询所有菜单用作管理
        WiModuleExample example = new WiModuleExample();
        List<WiModule> moduleList = moduleDao.selectByExample(example);
        resp.success(moduleList);
        return resp;
    }

    @Override
    public Response DeleteModule(Integer moduleId) {
        Response resp = new Response();
        moduleDao.deleteByPrimaryKey(moduleId);
        resp.success();
        return resp;
    }

}
