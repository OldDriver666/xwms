package com.fise.server.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.IMLocationMapper;
import com.fise.model.entity.IMLocation;
import com.fise.model.entity.IMLocationExample;
import com.fise.model.param.QueryUserParam;
import com.fise.server.user.ILocationService;

@Service
public class LocationServiceImpl implements ILocationService {

    @Autowired
    IMLocationMapper dbDao;

    @Override
    public Response queryUserHistory(QueryUserParam param) {
        Response resp = new Response();
        //Integer userId = param.getUserId();
        //String queryDate = param.getQueryDate();
        
        IMLocationExample example = new IMLocationExample();
        IMLocationExample.Criteria con = example.createCriteria();
        con.andUseridEqualTo(105328);
        example.setLimit(100);
        String tableName = "IMLocation_0";// + userId.intValue() % 8;
        System.out.println(tableName + " " + param.toString());
        List<IMLocation> data = dbDao.selectByExample(tableName, example);
        return resp.success(data);
    }

}
