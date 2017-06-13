package com.fise.controller.querydevice;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.QueryDeviceParam;
import com.fise.server.querydevice.IQueryDeviceService;

@RestController
@RequestMapping("/boss")
public class QueryDeviceController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IQueryDeviceService iQueryDeviceService;
    
    //通过设备账号查询
    @RequestMapping(value="/querydevice",method=RequestMethod.POST)
    public Response queryDeviceByAccount(@RequestBody @Valid QueryDeviceParam param){
        
        Response response=new Response();
        
        response=iQueryDeviceService.queryDeviceByAccount(param);
        
        return response;
    }
    
}
