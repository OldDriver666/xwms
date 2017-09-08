package com.fise.server.appstore;

import com.fise.base.Response;
import com.fise.model.entity.AppStore;

public interface IAppStoreService {
    //新增
    Response insert(AppStore param);
    
    //查询
    Response query();
    
    //请求下载地址
    Response queryDownload(Integer userId, String appId);
    
}
