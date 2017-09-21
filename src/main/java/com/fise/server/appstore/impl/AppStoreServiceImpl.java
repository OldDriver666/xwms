package com.fise.server.appstore.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.AppInformationMapper;
import com.fise.dao.AppStoreMapper;
import com.fise.model.entity.AppInformation;
import com.fise.model.entity.AppInformationExample;
import com.fise.model.entity.AppStore;
import com.fise.server.appstore.IAppStoreService;
import com.fise.utils.StringUtil;

@Service
public class AppStoreServiceImpl implements IAppStoreService{

    @Autowired
    AppStoreMapper appDao;

    @Autowired
    AppInformationMapper appInfoDao;
    
    @Override
    public Response insert(AppStore param) {
        Response resp = new Response();
        param.setAppId(StringUtil.md5(param.getAppName()));
        appDao.insert(param);
        resp.setData(param);
        return resp;
    }

    @Override
    public List<AppInformation> queryByIdList(List<Integer> idList) {
        AppInformationExample example = new AppInformationExample();
        AppInformationExample.Criteria con = example.createCriteria();
        con.andIdIn(idList);
        List<AppInformation> data = appInfoDao.selectByExample(example);
        return data;
    }

    @Override
    public AppInformation queryByAppIndex(String param) {
        AppInformationExample example = new AppInformationExample();
        AppInformationExample.Criteria con = example.createCriteria();
        con.andAppIndexEqualTo(param);
        List<AppInformation> data = appInfoDao.selectByExample(example);
        if(data.isEmpty())
            return null;
        else
            return data.get(0);
    }



}
