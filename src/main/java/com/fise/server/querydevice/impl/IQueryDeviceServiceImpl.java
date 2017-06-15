package com.fise.server.querydevice.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.CameraConfMapper;
import com.fise.dao.DeviceConfMapper;
import com.fise.dao.DeviceConfMotorMeterMapper;
import com.fise.dao.IMUserMapper;
import com.fise.dao.WatchConfMapper;
import com.fise.model.entity.DeviceConf;
import com.fise.model.entity.DeviceConfMotorMeter;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.entity.WatchConf;
import com.fise.model.param.QueryDeviceParam;
import com.fise.server.querydevice.IQueryDeviceService;
import com.fise.utils.StringUtil;


@Service
public class IQueryDeviceServiceImpl implements IQueryDeviceService{
    
    @Autowired
    IMUserMapper IMUserDao;
    
    @Autowired
    CameraConfMapper CameraConfDao;
    
    @Autowired
    DeviceConfMapper DeviceConfDao;
    
    @Autowired
    DeviceConfMotorMeterMapper DeviceConfMotorMeterDao;
    
    @Autowired
    WatchConfMapper WatchConfDao;
    
    @Override
    public Response queryDeviceByAccount(QueryDeviceParam param) {
        
        Response response=new Response();
        List<Object> resultlist=new ArrayList<>();
        
        IMUserExample example=new IMUserExample();
        Criteria criteria=example.createCriteria();
        criteria.andDepartidEqualTo(param.getDepartid());
        criteria.andTypeEqualTo(param.getType());
        
        if(!StringUtil.isEmpty(param.getAccount())){
            criteria.andNameEqualTo(param.getAccount());
        }
        
        if(!StringUtil.isEmpty(param.getPhone())){
            criteria.andPhoneEqualTo(param.getPhone());
        }
        
        List<IMUser> list=IMUserDao.selectByExample(example);
        
        if(list.size()==0){
            response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
        }
        
        if(param.getType()==25){
            
            for(IMUser user:list){
                WatchConf watch=WatchConfDao.selectByPrimaryKey(user.getId());
                resultlist.add(watch);
            }
            
            response.success(resultlist);
            return response;
        }
        
        if(param.getType()==19){
            
            for(IMUser user:list){
                DeviceConf deviceConf=DeviceConfDao.selectByPrimaryKey(user.getId());
                resultlist.add(deviceConf);
            }
            
            response.success(resultlist);
            return response;
        }
        
        if(param.getType()==23){
            
            for(IMUser user:list){
                DeviceConfMotorMeter motorMeter=DeviceConfMotorMeterDao.selectByPrimaryKey(user.getId());
                resultlist.add(motorMeter);
            }    
            
            response.success(resultlist);
            return response;
        }
        
        return response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
    }
    
}
