package com.fise.controller.electricfence;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.entity.ElectricFence;
import com.fise.model.param.ElectricFenceParam;
import com.fise.server.electricfence.IElectricFenceService;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/electricfence")
public class ElectricFenceController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IElectricFenceService electricFenceService;
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Response addElectricFence(@RequestBody @Valid ElectricFence record){
        
        Response response=new Response();
        logger.info(record.toString());
        
        if(record.getDeviceId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        
        response=electricFenceService.addElectricFence(record);
        return response;
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    public Response queryElectricFence(@RequestBody @Valid ElectricFenceParam param){
        Response response=new Response();
        logger.info(param.toString());
        
        if(StringUtil.isEmpty(param.getName())){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        
        response=electricFenceService.queryElectricFence(param);
        return response;
    }
    
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public Response updateElectricFence(@RequestBody @Valid ElectricFence record){
        
        Response response=new Response();
        logger.info(record.toString());
        
        if(record.getFenceId()==null ||record.getDeviceId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        
        response=electricFenceService.updateElectricFence(record);
        return response;
    }
    
    @RequestMapping(value="/del",method=RequestMethod.POST)
    public Response delElectricFence(@RequestBody @Valid ElectricFenceParam param){
        
        Response response=new Response();
        logger.info(param.toString());
        
        if(param.getFenceId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL); 
         }
        response=electricFenceService.delElectricFence(param);
        return response;
    }
}
