package com.fise.controller.accountmanage;

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
import com.fise.model.entity.WiAccountManage;
import com.fise.server.accountmanage.IAccountManageService;

@RestController
@RequestMapping("/boss/accountmanage")
public class AccountManageController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IAccountManageService accountManageService;
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Response insertAccount(@RequestBody @Valid WiAccountManage param){
        
        Response response=new Response();
        logger.info(param.toString());
        
        if(param.getDepartId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        response=accountManageService.insertAccount(param);
        return response;
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    public Response queryAccount(@RequestBody @Valid Map<String, Object> map){
        
        Response response=new Response();
        Integer id=(Integer) map.get("depart_id");
        response=accountManageService.queryAccount(id);
        return response;
    }
    
    @RequestMapping(value="/del",method=RequestMethod.POST)
    public Response delAccount(@RequestBody @Valid Map<String, Object> map){
        
        Response response=new Response();
        Integer id=(Integer) map.get("id");
        if(id==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        response=accountManageService.delAccount(id);
        return response;
    }
    
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public Response updateAccount(@RequestBody @Valid WiAccountManage param){
        Response response=new Response();
        
        if(param.getId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        response=accountManageService.updateAccount(param);
        return response;
    }
}
