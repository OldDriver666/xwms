package com.fise.controller.querydevice;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.QueryDeviceByAccountParam;
import com.fise.model.param.QueryDeviceByMobileParam;
import com.fise.model.param.QueryDeviceByTypeParam;
import com.fise.server.querydevice.IQueryDeviceService;

@RestController
@RequestMapping("/boss")
public class QueryDeviceController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IQueryDeviceService iQueryDeviceService;
    
    //通过设备账号查询
    @RequestMapping(value="/querydevicebyaccount",method=RequestMethod.POST)
    public Response queryDeviceByAccount(@RequestBody @Valid QueryDeviceByAccountParam param){
        
        Response response=new Response();
        
        response=iQueryDeviceService.queryDeviceByAccount(param);
        
        return response;
    }
    
    //通过号码查询
    @RequestMapping(value="/querydevicebymobile",method=RequestMethod.POST)
    public Response queryDeviceByMobile(@RequestBody @Valid QueryDeviceByMobileParam param){
        
        Response response=new Response();
        
        response=iQueryDeviceService.queryDeviceByMobile(param);
        
        return response;
    }
    
    //通过设备类型查询
    @RequestMapping(value="/querydevicebytype",method=RequestMethod.POST)
    public Response queryDeviceByType(@RequestBody @Valid QueryDeviceByTypeParam param){
        
        Response response=new Response();
        
        response=iQueryDeviceService.queryDeviceByType(param);
        
        return response;
    }
}
