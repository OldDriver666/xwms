package com.fise.server.appstore.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.AppStoreMapper;
import com.fise.model.entity.AppStore;
import com.fise.model.entity.AppStoreExample;
import com.fise.server.appstore.IAppStoreService;
import com.fise.utils.StringUtil;

@Service
public class AppStoreServiceImpl implements IAppStoreService{

    @Autowired
    AppStoreMapper appDao;

    @Override
    public Response insert(AppStore param) {
        Response resp = new Response();
        param.setAppId(StringUtil.md5(param.getAppName()));
        appDao.insert(param);
        resp.setData(param);
        return resp;
    }

    @Override
    public Response query() {
        Response resp = new Response();
        
        AppStoreExample example = new AppStoreExample();
        List<AppStore> data = appDao.selectByExample(example);
        resp.setData(data);
        return resp;
    }

    @Override
    public Response queryDownload(Integer userId, String appId) {
        Response resp = new Response();
        AppStoreExample example = new AppStoreExample();
        AppStoreExample.Criteria con = example.createCriteria();
        con.andAppIdEqualTo(appId);
        List<AppStore> data = appDao.selectByExample(example);
        if(data.size() > 0)
        {
            resp.setData(data.get(0));
        }
        else
        {
            resp.setErrorCode(ErrorCode.ERROR_DATABASE);
            resp.setMsg("没有对应的APP");
        }

        return resp;
    }


}
