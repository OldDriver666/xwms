package com.fise.server.module;

import com.fise.base.Response;
import com.fise.model.param.ModuleInsertParam;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleUpdateParam;

public interface IModuleService {
    /*查询用户可见模块*/
    Response QueryModule(ModuleQueryParam param);
    
    /*查询所有模块*/
    Response QueryModuleAll(ModuleQueryParam param);
    
    /*新增用户可见模块*/
    Response InsertModule(ModuleInsertParam param);
    
    /*修改用户可见模块*/
    Response UpdateModule(ModuleUpdateParam param);
    
    /*删除记录*/
    Response DeleteModule(Integer moduleId);
}
