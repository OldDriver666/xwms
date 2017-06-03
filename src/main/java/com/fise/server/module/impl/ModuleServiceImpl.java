package com.fise.server.module.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.WiModuleMapper;
import com.fise.model.entity.WiModule;
import com.fise.model.entity.WiModuleExample;
import com.fise.model.param.ModuleInsertParam;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleUpdateParam;
import com.fise.server.module.IModuleService;

@Service
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private WiModuleMapper moduleDao;
    
    @Override
    public Response QueryModeule(ModuleQueryParam param) {
        Response resp = new Response();
        WiModuleExample example = new WiModuleExample();
        //Criteria criteria = example.createCriteria();
        List<WiModule> moduleList = moduleDao.selectByExample(example);
        resp.success(moduleList);
        return resp;
    }

    @Override
    public Response InsertModeule(ModuleInsertParam param) {
        Response resp = new Response();
        return resp;
    }

    @Override
    public Response UpdateModeule(ModuleUpdateParam param) {
        Response resp = new Response();
        return resp;
    }

}
