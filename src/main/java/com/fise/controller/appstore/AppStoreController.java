package com.fise.controller.appstore;

import java.util.HashMap;
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
import com.fise.model.entity.AppStore;
import com.fise.server.appstore.IAppStoreService;

@RestController
@RequestMapping("/boss/store")
public class AppStoreController {

    private Logger logger = Logger.getLogger(getClass());

    @Resource
    IAppStoreService appStoreService;

    @IgnoreAuth
    @RequestMapping(value = "/applist", method = RequestMethod.POST)
    public Response getAppList() {

        Response response = new Response();
        response = appStoreService.query();
        return response;
    }

    @IgnoreAuth
    @RequestMapping(value = "/home/aplash", method = RequestMethod.POST)
    public Response getHomeSplash(@RequestBody @Valid Map<String, String> param) {
        Response response = new Response();

        return response;
    }

    @IgnoreAuth
    @RequestMapping(value = "/appinsert", method = RequestMethod.POST)
    public Response appInsert(@RequestBody @Valid AppStore param) {

        Response response = new Response();
        response = appStoreService.insert(param);
        return response;
    }

    /* 删除fise设备 */
    @IgnoreAuth
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public Response tryDownlad(@RequestBody @Valid HashMap<String, Object> param) {

        Response response = new Response();
        Integer userId;
        logger.info(param.toString());
        if (param.containsKey("user_id")) {
            userId = (Integer) param.get("user_id");
        } else {
            response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
            response.setMsg("没有用户ID");
            return response;
        }

        String appId;
        if (param.containsKey("app_id")) {
            appId = param.get("app_id").toString();
        } else {
            response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
            response.setMsg("没有应用ID");
            return response;
        }
        response = appStoreService.queryDownload(userId, appId);
        return response;
    }
}
