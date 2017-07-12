package com.fise.server.electricfence.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.ElectricFenceMapper;
import com.fise.dao.IMUserMapper;
import com.fise.model.entity.ElectricFence;
import com.fise.model.entity.ElectricFenceExample;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.param.ElectricFenceParam;
import com.fise.server.electricfence.IElectricFenceService;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;

@Service
public class ElectricFenceServiceImpl implements IElectricFenceService{
    
    @Autowired
    IMUserMapper userDao;
    
    @Autowired
    ElectricFenceMapper electricFenceDao;
    
    @Override
    public Response addElectricFence(ElectricFence record) {
        
        Response response=new Response();
        
        record.setCreated(DateUtil.getLinuxTimeStamp());
        record.setUpdated(DateUtil.getLinuxTimeStamp());
        
        electricFenceDao.insertSelective(record);
        return response.success();
    }

    @Override
    public Response queryElectricFence(ElectricFenceParam param) {
        
        Response response=new Response();
        
        IMUserExample example=new IMUserExample();
        IMUserExample.Criteria criteria=example.createCriteria();
        
        if(!StringUtil.isEmpty(param.getName())){
            criteria.andNameEqualTo(param.getName());
        }
        
        List<IMUser> list=userDao.selectByExample(example);
        
        if(list.size()==0){
            return response.failure(ErrorCode.ERROR_CAMERA_CONF_DEVICE_EXISTED);
        }
        
        ElectricFenceExample example1=new ElectricFenceExample();
        ElectricFenceExample.Criteria criteria1=example1.createCriteria();
        criteria1.andDeviceIdEqualTo(list.get(0).getId());
        
        List<ElectricFence> list1=electricFenceDao.selectByExample(example1);
        response.success(list1);
        
        return response;
    }

    @Override
    public Response updateElectricFence(ElectricFence record) {
        
        Response response=new Response();
        
        record.setUpdated(DateUtil.getLinuxTimeStamp());
        electricFenceDao.updateByPrimaryKeySelective(record);
        return response.success();
    }

    @Override
    public Response delElectricFence(ElectricFenceParam param) {
        
        Response response=new Response();
        
        electricFenceDao.deleteByPrimaryKey(param.getFenceId());
        return response.success();
    }

}
