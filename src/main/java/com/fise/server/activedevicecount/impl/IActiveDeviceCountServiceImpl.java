package com.fise.server.activedevicecount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.IMUserMapper;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.result.DeviceCountResult;
import com.fise.server.activedevicecount.IActiveDeviceCountService;

@Service
public class IActiveDeviceCountServiceImpl implements IActiveDeviceCountService{
    
    @Autowired
    IMUserMapper IMUserDao;
    
    @Override
    public Response getActiveDeviceCount() {
        
        Response response=new Response();
        DeviceCountResult result=new DeviceCountResult();
        
        IMUserExample example=new IMUserExample();
        Criteria criteria=example.createCriteria();
        
        criteria.andTypeLessThan(19);
        result.setActiveXM(IMUserDao.selectByExample(example).size());
        criteria.andOnlineStatusEqualTo(1);
        result.setOnlineXM(IMUserDao.selectByExample(example).size());
        
        IMUserExample example2=new IMUserExample();
        Criteria criteria2=example2.createCriteria();
        
        criteria2.andTypeGreaterThanOrEqualTo(19);
        result.setActiveDevice(IMUserDao.selectByExample(example2).size());
        criteria2.andOnlineStatusEqualTo(1);
        result.setOnlineDevice(IMUserDao.selectByExample(example2).size());
        
        response.success(result);
        return response;
    }

}
