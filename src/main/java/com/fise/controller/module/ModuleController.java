package com.fise.controller.module;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.param.ModuleInsertParam;
import com.fise.model.param.ModuleQueryParam;
import com.fise.model.param.ModuleUpdateParam;
import com.fise.server.auth.IAuthService;
import com.fise.server.module.IModuleService;

@RestController
@RequestMapping("/boss/module")
public class ModuleController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource 
    IModuleService moduleSvr;
    
    @Resource
    IAuthService authService;
    
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Response query(@RequestBody @Valid ModuleQueryParam param){
        Response resp = new Response();
        logger.info(param);
        resp = moduleSvr.QueryModule(param);
        return resp;
    }
    
    @RequestMapping(value = "/queryall", method = RequestMethod.POST)
    public Response queryAll(@RequestBody @Valid ModuleQueryParam param){
        Response resp = new Response();
        logger.info(param);
        resp = moduleSvr.QueryModuleAll(param);
        return resp;
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert(@RequestBody @Valid ModuleInsertParam param){
        Response resp = new Response();
        
        if(!authService.inserAuth()){
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        
        logger.info(param);
        resp = moduleSvr.InsertModule(param);
        return resp;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid ModuleUpdateParam param){
        Response resp = new Response();
        
        if(!authService.updateAuth()){
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        
        logger.info(param);
        resp = moduleSvr.UpdateModule(param);
        return resp;
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid Map<String,String> param){
        Response resp = new Response();
        
        if(!authService.updateAuth()){
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        
        logger.info(param.toString());
        if(param.get("module_id") == null){
            resp.failure(ErrorCode.ERROR_PARAM_NOT_VALID_EXCEPTION);
        } else {
            resp = moduleSvr.DeleteModule(new Integer(param.get("module_id")));
        }
        return resp;
    }
}
