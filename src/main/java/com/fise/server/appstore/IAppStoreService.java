package com.fise.server.appstore;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppInformation;

public interface IAppStoreService {
    //新增app
    public Response appInsert(Map<String ,Object> param,MultipartFile[] uploadPhoto,MultipartFile uploadApp);
    
    //修改app
    public Response appModify(Map<String ,Object> param);
    
    public Response queryByIdList(Page<AppChannel> page,List<Integer> idList);
    
    //删除app
    public Response appDelete(Integer appId); 
    
    //public String appSize(long size);
    
    //查询出所有的APP，并根据权重进行降序排列。
    public Response queryAppAll(Page<AppInformation> page);
    
    //查询出所有的Advert，并根据权重进行降序排序。
    public Response  queryAdvertAll();
    
    //查询出所有的channel，并根据权重进行降序排序。
    public Response queryChannelAll();
    
    //根据appName可进行模糊查询，在我的应用的首页查询出相关的App，并根据权重进行排序
    public Response queryByAppName(Page<AppInformation> page);
    
    public Response queryByAppName(String param);
    
    //热门搜索，返回四个热门的appName即可
    public Response hotSearch();
    
    //根据索引查询单个App
    public Response queryByAppId(Integer param);
}
