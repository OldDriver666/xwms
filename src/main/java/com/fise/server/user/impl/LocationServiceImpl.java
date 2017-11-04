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
import com.fise.utils.DateUtil;

@Service
public class LocationServiceImpl implements ILocationService {

    @Autowired
    IMLocationMapper dbDao;

    @Override
    public Response queryUserHistory(QueryUserParam param) {
        Response resp = new Response();
        Integer userId = param.getUserId();
        String queryDate = param.getQueryDate();
        long startTime = DateUtil.getDateline(queryDate);
        long endTime = startTime + 24 * 60 * 60;
        
        IMLocationExample example = new IMLocationExample();
        IMLocationExample.Criteria con = example.createCriteria();
        con.andUseridEqualTo(userId);
        con.andCreatedBetween(Integer.parseInt(String.valueOf(startTime)), Integer.parseInt(String.valueOf(endTime)));

        String tableName = "IMLocation_" + userId.intValue() % 8;
        List<IMLocation> data = dbDao.selectByExample(tableName, example);
        return resp.success(data);
    }

}
