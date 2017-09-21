package com.fise.server.activedevicecount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.IMUserMapper;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.param.DeviceCountParam;
import com.fise.model.result.DeviceCountResult;
import com.fise.server.activedevicecount.IActiveDeviceCountService;

@Service
public class ActiveDeviceCountServiceImpl implements IActiveDeviceCountService{
    
    @Autowired
    IMUserMapper IMUserDao;
    
    @Override
    public Response getActiveDeviceCount(DeviceCountParam param) {
        
        Response response=new Response();
        DeviceCountResult result=new DeviceCountResult();
        
        IMUserExample example=new IMUserExample();
        Criteria criteria=example.createCriteria();
        if(param.getDepartid() != 0){
            criteria.andDepartidEqualTo(param.getDepartid());
        }
        criteria.andCompanyidEqualTo(param.getCompanyId());
        criteria.andTypeLessThan(19);
        result.setActiveXM((int)IMUserDao.countByExample(example));
        criteria.andOnlineStatusEqualTo(1);
        result.setOnlineXM((int)IMUserDao.countByExample(example));
        
        IMUserExample example2=new IMUserExample();
        Criteria criteria2=example2.createCriteria();
        
        if(param.getDepartid() != 0){
            criteria2.andDepartidEqualTo(param.getDepartid());
        }
        criteria2.andCompanyidEqualTo(param.getCompanyId());
        criteria2.andTypeGreaterThanOrEqualTo(19);
        result.setActiveDevice((int)IMUserDao.countByExample(example2));
        criteria2.andOnlineStatusEqualTo(1);
        result.setOnlineDevice((int)IMUserDao.countByExample(example2));
        
        response.success(result);
        return response;
    }

}
