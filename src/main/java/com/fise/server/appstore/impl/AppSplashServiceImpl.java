package com.fise.server.appstore.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.AppSplashMapper;
import com.fise.model.entity.AppSplash;
import com.fise.model.entity.AppSplashExample;
import com.fise.server.appstore.IAppSplashService;

@Service
public class AppSplashServiceImpl implements IAppSplashService {

    @Autowired
    AppSplashMapper splashDao;
    
    @Override
    public Response insertSplash() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AppSplash> querySpalsh() {
        List<AppSplash> data = new ArrayList<AppSplash>();
        AppSplashExample example = new AppSplashExample();
        AppSplashExample.Criteria con = example.createCriteria();
        con.andStatusEqualTo(1);
        data = splashDao.selectByExample(example);
        return data;
    }

}
