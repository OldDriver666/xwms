package com.fise.controller.querydevice;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.param.QueryDeviceParam;
import com.fise.server.deviceconfig.IDeviceConfigService;
import com.qq.jutil.string.StringUtil;

@RestController
@RequestMapping("/boss")
public class QueryDeviceController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IDeviceConfigService iQueryDeviceService;
    
    //通过设备账号查询
    @RequestMapping(value="/querydevice",method=RequestMethod.POST)
    public Response queryDeviceByAccount(@RequestBody @Valid QueryDeviceParam param){
        
        Response response=new Response();
        logger.info(param.toString());
        if(StringUtil.isEmpty(param.getAccount()) && StringUtil.isEmpty(param.getPhone()) && param.getDepartid() == null){
            response.setErrorCode(ErrorCode.ERROR_PARAM_VALIDATION_EXCEPTION);
            response.setMsg("请输入查询条件");
        } else {
            response=iQueryDeviceService.queryDeviceByAccount(param);
        }

        return response;
    }
    
}
