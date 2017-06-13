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
import com.fise.model.entity.DeviceConfExample;
import com.fise.model.entity.DeviceConfMotorMeter;
import com.fise.model.entity.DeviceConfMotorMeterExample;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.entity.WatchConf;
import com.fise.model.entity.WatchConfExample;
import com.fise.model.param.QueryDeviceByAccountParam;
import com.fise.model.param.QueryDeviceByMobileParam;
import com.fise.model.param.QueryDeviceByTypeParam;
import com.fise.server.querydevice.IQueryDeviceService;
import com.fise.utils.JsonUtil;


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
    public Response queryDeviceByAccount(QueryDeviceByAccountParam param) {
        
        Response response=new Response();
           
        IMUserExample example=new IMUserExample();
        Criteria criteria=example.createCriteria();
        criteria.andNameEqualTo(param.getAccount());
        
        List<IMUser> list=IMUserDao.selectByExample(example);
        
        if(list.size()==0){
            response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
        }
        
        IMUser user=list.get(0);
        Integer id=user.getId();
        
        if(user.getType()==25){
            WatchConf watch=WatchConfDao.selectByPrimaryKey(id);
            response.success(watch);
            return response;
        }
        
        if(user.getType()==19){
            DeviceConf deviceConf=DeviceConfDao.selectByPrimaryKey(id);
            response.success(deviceConf);
            return response;
        }
        
        if(user.getType()==23){
            DeviceConfMotorMeter motorMeter=DeviceConfMotorMeterDao.selectByPrimaryKey(id);
            response.success(motorMeter);
            return response;
        }
        
        return response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
    }

    @Override
    public Response queryDeviceByMobile(QueryDeviceByMobileParam param) {
        
        Response response=new Response();
        
        List<Object> list=new ArrayList<>();
        
        DeviceConfExample example=new DeviceConfExample();
        DeviceConfExample.Criteria criteria=example.createCriteria();
        criteria.andMobileEqualTo(param.getMobile());
        List<DeviceConf> devicevonflist=DeviceConfDao.selectByExample(example);
        if(devicevonflist.size()!=0){
            list.add(devicevonflist.get(0));
        }
        
        DeviceConfMotorMeterExample motorMeterExample=new DeviceConfMotorMeterExample();
        DeviceConfMotorMeterExample.Criteria criteria2=motorMeterExample.createCriteria();
        criteria2.andMobileEqualTo(param.getMobile());
        List<DeviceConfMotorMeter> motermeterlist=DeviceConfMotorMeterDao.selectByExample(motorMeterExample);
        if(motermeterlist.size()!=0){
            list.add(motermeterlist.get(0));
        }
        
        WatchConfExample watchConfExample=new WatchConfExample();
        WatchConfExample.Criteria criteria3=watchConfExample.createCriteria();
        criteria3.andMobileEqualTo(param.getMobile());
        List<WatchConf> watchConflist=WatchConfDao.selectByExample(watchConfExample);
        if(watchConflist.size()!=0){
            list.add(watchConflist.get(0));
        }
        
        if(list==null){
            return response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
        }
        
        return response.success(list);
    }

    @Override
    public Response queryDeviceByType(QueryDeviceByTypeParam param) {
        
        Response response=new Response();
        
        if(param.getType()==25){
            List<WatchConf> list=WatchConfDao.selectByExample(null);
            response.success(list);
            return response;
        }
        
        if(param.getType()==19){
            List<DeviceConf> list=DeviceConfDao.selectByExample(null);
            response.success(list);
            return response;
        }
        
        if(param.getType()==23){
            List<DeviceConfMotorMeter> list=DeviceConfMotorMeterDao.selectByExample(null);
            response.success(list);
            return response;
        }
        
        return response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
    }

    
}
