package com.fise.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.DeviceConf;
import com.fise.model.param.QueryDeviceParam;
import com.fise.model.param.QueryUserParam;
import com.fise.model.result.UserDetail;
import com.fise.server.auth.IAuthService;
import com.fise.server.deviceconfig.IDeviceConfigService;
import com.fise.server.user.ILocationService;
import com.fise.server.user.IUserService;
import com.fise.utils.StringUtil;


@RestController
@RequestMapping("/boss/user")
public class UserController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IUserService IQueryUserService;
    
    @Resource
    IDeviceConfigService IDevConfSvr;
    
    @Resource
    IAuthService authService;
    
    @Resource
    ILocationService locationSvr;
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    public Response queryUserInfo(@RequestBody @Valid Page<QueryUserParam> param){
        
        Response response=new Response();
//        param.getParam().setCompanyId(HttpContext.getCompanyId());
        logger.info(param.toString());
        response=IQueryUserService.queryUserByPage(param);
       
        
        return response;
    }
    
    @IgnoreAuth
    @RequestMapping(value="/location", method=RequestMethod.POST)
    public Response queryUserLocation(@RequestBody @Valid QueryUserParam param){
        Response response=new Response();
        logger.info(param.toString());
        if(param.getCompanyId() == null){
            response.setErrorCode(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
            response.setMsg("查询组织标示为空");
            return response;
        }
        response=IQueryUserService.queryUserLocation(param);
        return response;
    }
    
    @IgnoreAuth
    @RequestMapping(value="/history", method=RequestMethod.POST)
    public Response queryLocationHistory(@RequestBody @Valid QueryUserParam param){
        Response response=new Response();
        if(param.getCompanyId() == null || StringUtil.isEmpty(param.getQueryDate()) || param.getUserId() == null){
            response.setErrorCode(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
            response.setMsg("查询参数为空");
            return response;
        }

        response=locationSvr.queryUserHistory(param);
        return response;
    }
    
    @IgnoreAuth
    @RequestMapping(value="/detail", method=RequestMethod.POST)
    public Response queryUserDetail(@RequestBody @Valid QueryUserParam param){
        Response response=new Response();

        response=IQueryUserService.queryUserDetail(param);
        if(response.getCode() != 0){
            return response;
        }
        UserDetail data = (UserDetail)response.getData();
        //查询设备使用者的管理员信息
        QueryDeviceParam paramConf = new QueryDeviceParam();
        paramConf.setDepartid(param.getDepartId());
        paramConf.setDeviceId(data.getUid());
        //TODO 仅查询定位卡片机类型 
        paramConf.setType(19);
        response = IDevConfSvr.queryDeviceByAccount(paramConf);
        DeviceConf conf = ((Map<String, DeviceConf>)response.getData()).get("base_info");
        data.setSos_phone(conf.getMobile());
        //查询用户计步数据
        data.setStep_cnt(99);
        response.setData(data);
        return response;
    }
}
