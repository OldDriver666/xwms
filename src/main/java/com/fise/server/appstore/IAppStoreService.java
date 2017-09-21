package com.fise.server.appstore;

import java.util.List;

import com.fise.base.Response;
import com.fise.model.entity.AppInformation;
import com.fise.model.entity.AppStore;

public interface IAppStoreService {
    //新增
    Response insert(AppStore param);
    
    //查询
    List<AppInformation> queryByIdList(List<Integer> idList);

    AppInformation queryByAppIndex(String param);

}
