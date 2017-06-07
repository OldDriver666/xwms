package com.fise.server.serviceconf.impl;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.IMServiceConfMapper;
import com.fise.model.entity.IMServiceConf;
import com.fise.model.entity.IMServiceConfExample;
import com.fise.model.entity.IMServiceConfExample.Criteria;
import com.fise.model.param.ServiceConfParam;
import com.fise.server.serviceconf.IServiceConfService;
import com.fise.utils.StringUtil;

@Service
public class IServiceConfServiceImpl implements IServiceConfService{
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	IMServiceConfMapper IMServiceConfDao;

	@Override
	public Response insertServiceConf(IMServiceConf record) {
		
		Response response=new Response();
		
		if(StringUtil.isEmpty(record.getServiceName())||StringUtil.isEmpty(record.getServicePwd())){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		//判断添加的servicename是否已经存在
		IMServiceConfExample exampleIME=new IMServiceConfExample();
		Criteria criteriaIME=exampleIME.createCriteria();
		criteriaIME.andServiceNameEqualTo(record.getServiceName());
		List<IMServiceConf> fisedevicelist=IMServiceConfDao.selectByExample(exampleIME);
		if(fisedevicelist.size()!=0){
			response.failure(ErrorCode.ERROR_SERVICE_CONF_NAME_EXISTED);
			return response;
		}		
		
		//更新数据信息
		long nowtime=System.currentTimeMillis()/1000;
		record.setAuthcode(StringUtil.makeCode(32,false));
		record.setUpdateTime((int)nowtime);
		IMServiceConfDao.insertSelective(record);
		
		response.success();
		return response;
	}

	@Override
	public Response selectServiceConf(ServiceConfParam param) {
		
		Response response=new Response();
		
		if(StringUtil.isEmpty(param.getServiceName()) || StringUtil.isEmpty(param.getServicePwd())){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		IMServiceConfExample example=new IMServiceConfExample();
		Criteria criteria=example.createCriteria();
		criteria.andServiceNameEqualTo(param.getServiceName());
		criteria.andServicePwdEqualTo(param.getServicePwd());
		
		List<IMServiceConf> list=IMServiceConfDao.selectByExample(example);
		
		if(list.size()==0){
			response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
			return response;
		}
		
		response.success(list.get(0));
		return response;
	}

	@Override
	public Response delServiceConf(ServiceConfParam param) {
		
		Response response=new Response();
		
		if(param.getConfigid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		IMServiceConfDao.deleteByPrimaryKey(param.getConfigid());
		
		return response.success();
	}

	@Override
	public Response updateServiceConf(IMServiceConf record) {
		
		Response response=new Response();
		
		if(record.getConfigid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		if(!StringUtil.isEmpty(record.getServiceName())){
			//判断添加的servicename是否已经存在
			IMServiceConfExample exampleIME=new IMServiceConfExample();
			Criteria criteriaIME=exampleIME.createCriteria();
			criteriaIME.andServiceNameEqualTo(record.getServiceName());
			List<IMServiceConf> fisedevicelist=IMServiceConfDao.selectByExample(exampleIME);
			
			if(fisedevicelist.size()!=0){
				if(record.getConfigid().equals(fisedevicelist.get(0).getConfigid())){
					
				}else{
					response.failure(ErrorCode.ERROR_SERVICE_CONF_NAME_EXISTED);
					return response;
				}
			}
		}
		
		//更新数据信息
		long nowtime=System.currentTimeMillis()/1000;
		record.setUpdateTime((int)nowtime);
		record.setAuthcode(null);
		IMServiceConfDao.updateByPrimaryKeySelective(record);
		
		response.success();
		return response;		

	}
}
