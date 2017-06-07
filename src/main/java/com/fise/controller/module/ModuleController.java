package com.fise.controller.module;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.ModuleInsertParam;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleUpdateParam;
import com.fise.server.module.IModuleService;

@RestController
@RequestMapping("/boss/module")
public class ModuleController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource IModuleService moduleSvr;
    
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Response query(@RequestBody @Valid ModuleQueryParam param){
        Response resp = new Response();
        logger.debug(param);
        resp = moduleSvr.QueryModeule(param);
        return resp;
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert(@RequestBody @Valid ModuleInsertParam param){
        Response resp = new Response();
        logger.debug(param);
        resp = moduleSvr.InsertModeule(param);
        return resp;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid ModuleUpdateParam param){
        Response resp = new Response();
        logger.debug(param);
        resp = moduleSvr.UpdateModeule(param);
        return resp;
    }
}
