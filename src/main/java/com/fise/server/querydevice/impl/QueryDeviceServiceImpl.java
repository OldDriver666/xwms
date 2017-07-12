package com.fise.server.querydevice.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.CameraConfMapper;
import com.fise.dao.DeviceConfMapper;
import com.fise.dao.DeviceConfMotorMeterMapper;
import com.fise.dao.DeviceControlMapper;
import com.fise.dao.DeviceCrontabMapper;
import com.fise.dao.ElectricFenceMapper;
import com.fise.dao.IMUserMapper;
import com.fise.dao.WatchConfMapper;
import com.fise.model.entity.DeviceConf;
import com.fise.model.entity.DeviceConfMotorMeter;
import com.fise.model.entity.DeviceControl;
import com.fise.model.entity.DeviceControlExample;
import com.fise.model.entity.DeviceCrontab;
import com.fise.model.entity.DeviceCrontabExample;
import com.fise.model.entity.ElectricFence;
import com.fise.model.entity.ElectricFenceExample;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.entity.WatchConf;
import com.fise.model.param.QueryDeviceParam;
import com.fise.server.querydevice.IQueryDeviceService;
import com.fise.utils.StringUtil;


@Service
public class QueryDeviceServiceImpl implements IQueryDeviceService{
    
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
    
    @Autowired
    DeviceCrontabMapper crontabDao;
    
    @Autowired
    DeviceControlMapper controlDao;
    
    @Autowired
    ElectricFenceMapper fenceDao;
    
    @Override
    public Response queryDeviceByAccount(QueryDeviceParam param) {
        
        Response response=new Response();

        Map<String, Object> data = new HashMap<String, Object>();
        Integer nDeviceId = new Integer(0);
        
        //TODO 数据检测是否有权限查询
        if( param.getDeviceId() == null ){
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
            if(list.size()==0 || list.size() != 1){
                response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
                response.setMsg("数据错误请联系管理员");
                return response;
            } else {
                nDeviceId = list.get(0).getId();
            }
        } else {
            nDeviceId = param.getDeviceId();
        }
        
        if(param.getType()==25){
            WatchConf baseInfo=WatchConfDao.selectByPrimaryKey(nDeviceId);
            data.put("base_info", baseInfo);
            DeviceCrontabExample ex2=new DeviceCrontabExample();
            DeviceCrontabExample.Criteria crit2=ex2.createCriteria();
            crit2.andDeviceIdEqualTo(nDeviceId);
            crit2.andStatusNotEqualTo(3);
            List<DeviceCrontab> crontab = crontabDao.selectByExample(ex2);
            data.put("crontab", crontab);
        }
        
        if(param.getType()==19){
            DeviceConf baseInfo=DeviceConfDao.selectByPrimaryKey(nDeviceId);
            data.put("base_info", baseInfo);
        }
        
        if(param.getType()==20){
            DeviceConfMotorMeter baseInfo=DeviceConfMotorMeterDao.selectByPrimaryKey(nDeviceId);
            data.put("base_info", baseInfo);
        }
        
        DeviceControlExample conExa=new DeviceControlExample();
        DeviceControlExample.Criteria conCrit=conExa.createCriteria();
        conCrit.andDeviceIdEqualTo(nDeviceId);
        conCrit.andStatusNotEqualTo(3);
        List<DeviceControl> control = controlDao.selectByExample(conExa);
        data.put("control", control);
        
        ElectricFenceExample fenceExa=new ElectricFenceExample();
        ElectricFenceExample.Criteria fenceCrit=fenceExa.createCriteria();
        fenceCrit.andDeviceIdEqualTo(nDeviceId);
        fenceCrit.andStatusNotEqualTo(3);
        List<ElectricFence> fence = fenceDao.selectByExample(fenceExa);
        data.put("electri_fence", fence);
        
        return response.success(data);
    }

}
