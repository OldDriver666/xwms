package com.fise.controller.appstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppInformation;
import com.fise.model.entity.AppSplash;
import com.fise.model.entity.AppStore;
import com.fise.model.result.AppBaseResult;
import com.fise.model.result.AppChannelResult;
import com.fise.model.result.AppDetailResult;
import com.fise.model.result.StoreSplashResult;
import com.fise.server.appstore.IAppChannelService;
import com.fise.server.appstore.IAppSplashService;
import com.fise.server.appstore.IAppStoreService;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/store")
public class AppStoreController {

    private Logger logger = Logger.getLogger(getClass());

    @Resource
    IAppStoreService appSvr;
    
    @Resource
    IAppSplashService splashSvr;
    
    @Resource
    IAppChannelService channelSvr;

    
    @IgnoreAuth
    @RequestMapping(value = "/appinfo", method = RequestMethod.POST)
    public Response getAppInfo(@RequestBody @Valid Map<String, String> param) {
        Response resp = new Response();
        if(StringUtil.isEmpty(param.get("app_index"))){
            return resp.failure(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
        }
        AppInformation data = appSvr.queryByAppIndex(param.get("app_index"));
        if(data == null){
            resp.failure(ErrorCode.ERROR_PARAM_MEMBER_MOBILE_IS_EMPTY);
            resp.setMsg("APP信息不存在");
            return resp;
        }
        AppDetailResult result = new AppDetailResult();
        result.init(data);
        resp.success(result);
        return resp;
    }
    
    @IgnoreAuth
    @RequestMapping(value = "/channel", method = RequestMethod.POST)
    public Response getChannelInfo(@RequestBody @Valid Map<String, Object> param) {
        Response resp = new Response();
        if(param.get("channel_index") == null){
            return resp.failure(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
        }
        Integer channelId = (Integer)param.get("channel_index");
        AppChannelResult data = new AppChannelResult();
        AppChannel channel = new AppChannel();
        channel = channelSvr.getChannelInfo(channelId);
        if(channel == null){
            resp.failure(ErrorCode.ERROR_PARAM_MEMBER_MOBILE_IS_EMPTY);
            resp.setMsg("频道信息不存在");
            return resp;
        }
        
        data.init(channel);
        
        List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
        List<Integer> appIdList = channelSvr.getChannelAppId(channelId);

        //为了避免错误
        appIdList.add(0);
        List<AppInformation> appList = appSvr.queryByIdList(appIdList);
        for (int i = 0; i < appList.size(); i++) {
            AppBaseResult appBase = new AppBaseResult();
            appBase.init(appList.get(i));
            appData.add(appBase);
        }
        data.setAppList(appData);
        
        return resp.success(data);
    }
    
    @IgnoreAuth
    @RequestMapping(value = "/home/aplash", method = RequestMethod.POST)
    public Response getHomeSplash(@RequestBody @Valid Map<String, String> param) {
        Response resp = new Response();
        List<AppSplash> data = splashSvr.querySpalsh();
        resp.success(data);
        return resp;
    }

    @IgnoreAuth
    @RequestMapping(value = "/appinsert", method = RequestMethod.POST)
    public Response appInsert(@RequestBody @Valid AppStore param) {

        Response response = new Response();
        response = appSvr.insert(param);
        return response;
    }

    @IgnoreAuth
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public Response homePage(@RequestBody @Valid Map<String, String> param) {

        Response resp = new Response();
        Map<String, Object> data = new HashMap<String,Object>();
        List<AppSplash> splashData = splashSvr.querySpalsh();
        List<StoreSplashResult> splashRet = new ArrayList<StoreSplashResult>();
        for(AppSplash tmp : splashData){
            StoreSplashResult splash = new StoreSplashResult();
            splash.init(tmp);
            splashRet.add(splash);
        }
        data.put("splash", splashRet);
        
        List<AppChannel> channelData = channelSvr.query();
        List<AppChannelResult> baseData = new ArrayList<AppChannelResult>();
        List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
        for(AppChannel tmp2 : channelData){
            AppChannelResult channel = new AppChannelResult();
            appData.clear();
            channel.init(tmp2);
            if(tmp2.getChannelType() != 0){
                List<Integer> appIdList = channelSvr.getChannelAppId(tmp2.getId());
                List<AppInformation> appList = appSvr.queryByIdList(appIdList);
                for(int i=0;i<appList.size();i++){
                    AppBaseResult appBase = new AppBaseResult();
                    appBase.init(appList.get(i));
                    appData.add(appBase);
                }
                channel.setAppList(appData);
                baseData.add(channel);
            } else {
                baseData.add(channel);
            }
        }
        data.put("channel", baseData);
        resp.success(data);
        return resp;
    }
}
