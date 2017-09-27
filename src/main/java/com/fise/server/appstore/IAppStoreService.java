package com.fise.server.appstore;

import java.util.List;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.entity.AppAdvert;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppInformation;
import com.fise.model.entity.AppStore;

public interface IAppStoreService {
    //新增
   public Response insert(AppStore param);
    
    //查询
    List<AppInformation> queryByIdList(List<Integer> idList);
    
    //查询出所有的APP，并根据权重进行降序排列。
    public Response queryAppAll(Page<AppInformation> page);
    
    //查询出所有的Advert，并根据权重进行降序排序。
    public Response  queryAdvertAll();
    
    //查询出所有的channel，并根据权重进行降序排序。
    public Response queryChannelAll();
    
    //根据appName可进行模糊查询，在我的应用的首页查询出相关的App，并根据权重进行排序
    public Response queryByAppName(Page<AppInformation> page);
    
    //热门搜索，返回四个热门的appName即可
    public Response hotSearch();
    
    AppInformation queryByAppIndex(String param);
}
