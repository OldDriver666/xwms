package com.fise.server.deviceversion.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.IMDevcieVersionMapper;
import com.fise.model.entity.IMDevcieVersion;
import com.fise.model.entity.IMDevcieVersionExample;
import com.fise.model.entity.IMDevcieVersionExample.Criteria;
import com.fise.model.param.DeviceVersionParam;
import com.fise.server.deviceversion.IDeviceVersionService;
import com.fise.utils.StringUtil;

@Service
public class IDeviceVersionServiceImpl implements IDeviceVersionService{
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	IMDevcieVersionMapper deviceVersionDao;
	
	@Override
	public Response insertDeviceVersion(IMDevcieVersion record) {
		
		Response response=new Response();
		
		if(record.getDepartid()==null || record.getDevType()==null || StringUtil.isEmpty(record.getDevVersion())||StringUtil.isEmpty(record.getUpdateUrl())){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		deviceVersionDao.insertSelective(record);
		response.success();
		
		return response;
	}

	@Override
	public Response queryDeviceVersion(DeviceVersionParam param) {
		
		Response response=new Response();
		
		if(param.getDepartid()==null && param.getDevType()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		IMDevcieVersionExample example=new IMDevcieVersionExample();
		Criteria criteria=example.createCriteria();
		
		if(param.getDepartid()!=null){
			criteria.andDepartidEqualTo(param.getDepartid());
		}
		
		if(param.getDevType()!=null){
			criteria.andDevTypeEqualTo(param.getDevType());
		}
		
		List<IMDevcieVersion> list=deviceVersionDao.selectByExample(example);
		
		if(list.size()==0){
			return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
		}
		
		response.success(list);
		
		return response;
	}

	@Override
	public Response delDeviceVersion(DeviceVersionParam param) {
		
		Response response=new Response();
		
		if(param.getVersionid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		deviceVersionDao.deleteByPrimaryKey(param.getVersionid());
		return response.success();
	}

	@Override
	public Response updateDeviceVersion(IMDevcieVersion record) {
		
		Response response=new Response();
		
		if(record.getVersionid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		deviceVersionDao.updateByPrimaryKeySelective(record);
		return response.success();
	}

}
