package com.fise.server.appstore;

import java.util.List;

import com.fise.base.Response;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppStore;

public interface IAppChannelService {
    //新增
    Response insert(AppStore param);
    
    //查询
    List<AppChannel> query();
    
    List<Integer> getChannelAppId(Integer channelId);
    
    AppChannel getChannelInfo(Integer channelId);
}
