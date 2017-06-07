package com.fise.server.splash.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.IMSplashMapper;
import com.fise.model.entity.IMSplash;
import com.fise.model.entity.IMSplashExample;
import com.fise.model.entity.IMSplashExample.Criteria;
import com.fise.model.param.SplashParam;
import com.fise.server.splash.ISplashService;
import com.fise.utils.StringUtil;

@Service
public class ISplashServiceImpl implements ISplashService{
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	IMSplashMapper splashDao;
	
	@Override
	public Response insertSplash(IMSplash record) {
		
		Response response=new Response();
		
		if(StringUtil.isEmpty(record.getName())){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		//更新数据
		long nowtime=System.currentTimeMillis()/1000;
		record.setCreated((int)nowtime);
		record.setUpdated((int)nowtime);
		splashDao.insertSelective(record);
		
		return response.success();
	}

	@Override
	public Response querySplash(SplashParam param) {
		
		Response response=new Response();
		
		if(StringUtil.isEmpty(param.getName())){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		IMSplashExample example=new IMSplashExample();
		Criteria criteria=example.createCriteria();
		criteria.andNameEqualTo(param.getName());
		List<IMSplash> list=splashDao.selectByExample(example);
		
		if(list.size()==0){
			return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
		}
		
		response.success(list.get(0));
		return response;
	}

	@Override
	public Response delSplash(SplashParam param) {
		
		Response response=new Response();
		
		if(param.getSplashid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		splashDao.deleteByPrimaryKey(param.getSplashid());
		
		return response.success();
	}

	@Override
	public Response updateSplash(IMSplash record) {
		
		Response response=new Response();
		
		if(record.getSplashid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		long nowtime=System.currentTimeMillis()/1000;
		record.setCreated(null);
		record.setUpdated((int)nowtime);
		splashDao.updateByPrimaryKeySelective(record);
		
		return response.success();
	}
	
}
