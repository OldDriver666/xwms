package com.fise.server.module;

import com.fise.base.Response;
import com.fise.model.param.ModuleInsertParam;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleUpdateParam;

public interface IModuleService {
    /*查询用户可见模块*/
    Response QueryModeule(ModuleQueryParam param);
    
    /*新增用户可见模块*/
    Response InsertModeule(ModuleInsertParam param);
    
    /*修改用户可见模块*/
    Response UpdateModeule(ModuleUpdateParam param);
}
