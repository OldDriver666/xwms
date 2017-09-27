package com.fise.server.appstore.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.AppChannelListMapper;
import com.fise.dao.AppChannelMapper;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppChannelExample;
import com.fise.model.entity.AppChannelList;
import com.fise.model.entity.AppChannelListExample;
import com.fise.model.entity.AppStore;
import com.fise.server.appstore.IAppChannelService;

@Service
public class AppChannelServiceImpl implements IAppChannelService {

    @Autowired
    AppChannelMapper channelDao;
    
    @Autowired
    AppChannelListMapper listDao;
    
    @Override
    public Response insert(AppStore param) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AppChannel> query() {
        AppChannelExample example = new AppChannelExample();
        AppChannelExample.Criteria con = example.createCriteria();
        con.andStatusEqualTo(1);
        List<AppChannel> data = channelDao.selectByExample(example);
        return data;
    }

    @Override
    public List<Integer> getChannelAppId(Integer channelId) {
        List<Integer> data = new ArrayList<Integer>();
        AppChannelListExample example = new AppChannelListExample();
        AppChannelListExample.Criteria con = example.createCriteria();
        con.andChannelIdEqualTo(channelId);
        con.andStatusEqualTo(1);
        List<AppChannelList> chanList = listDao.selectByExample(example);
        for(AppChannelList tmp : chanList){
            data.add(tmp.getAppId());
        }
        return data;
    }

    @Override
    public AppChannel getChannelInfo(Integer channelId) {
        AppChannelExample example = new AppChannelExample();
        AppChannelExample.Criteria con = example.createCriteria();
        con.andStatusEqualTo(1);
        con.andIdEqualTo(channelId);
        List<AppChannel> data = channelDao.selectByExample(example);
        if(data.isEmpty())
            return null;
        else
            return data.get(0);
    }

}
