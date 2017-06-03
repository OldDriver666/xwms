package com.fise.server.fisedevice.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.FiseDeviceMapper;

import com.fise.model.entity.FiseDevice;
import com.fise.model.entity.FiseDeviceExample;
import com.fise.model.entity.FiseDeviceExample.Criteria;
import com.fise.model.param.QueryFiseDeviceParam;
import com.fise.server.fisedevice.FiseDeviceService;
import com.fise.utils.StringUtil;

@Service
public class FiseDeviceServiceImpl implements FiseDeviceService{
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	FiseDeviceMapper fiseDevicedao;
	
	@Override
	public Response insertFiseDevice(FiseDevice record) {
		
		Response response=new Response();
		
		if(StringUtil.isEmpty(record.getIme())||record.getStatus()==null||StringUtil.isEmpty(record.getAccount())||record.getType()==null||record.getDepartid()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		//判断添加的设备的IME是否已经存在
		FiseDeviceExample exampleIME=new FiseDeviceExample();
		Criteria criteriaIME=exampleIME.createCriteria();
		criteriaIME.andImeEqualTo(record.getIme());
		List<FiseDevice> fisedevicelist=fiseDevicedao.selectByExample(exampleIME);
		if(fisedevicelist.size()!=0){
			response.failure(ErrorCode.ERROR_FISE_DEVICE_IME_EXISTED);
			return response;
		}
		
		//判断添加的设备的账号是否已经注册
		FiseDeviceExample exampleAccount=new FiseDeviceExample();
		Criteria criteriaAccount=exampleAccount.createCriteria();
		criteriaAccount.andAccountEqualTo(record.getAccount());
		List<FiseDevice> list=fiseDevicedao.selectByExample(exampleAccount);
		if(list.size()!=0){
			response.failure(ErrorCode.ERROR_FISE_DEVICE_ACCOUNT_EXISTED);
			return response;
		}
		
		//更新设备相关信息
		long nowtime=System.currentTimeMillis() / 1000;
		record.setUpdated((int)nowtime);
		record.setCreated((int)nowtime);
		fiseDevicedao.insert(record);
		response.success();
		return response;
	}

	@Override
	public Response queryFiseDevice(QueryFiseDeviceParam param) {
		
		Response response=new Response();
		
		if(StringUtil.isEmpty(param.getAccount()) && StringUtil.isEmpty(param.getIme()) && param.getDepartid()!=null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		FiseDeviceExample example=new FiseDeviceExample();
		Criteria criteria=example.createCriteria();
		criteria.andDepartidEqualTo(param.getDepartid());
		
		if(!StringUtil.isEmpty(param.getIme())){
			criteria.andImeEqualTo(param.getIme());
		}
		
		if(!StringUtil.isEmpty(param.getAccount())){
			criteria.andAccountEqualTo(param.getAccount());	
		} 
		
		List<FiseDevice> fisedevicelist=fiseDevicedao.selectByExample(example);
		
		if(fisedevicelist.size()!=0){
			response.success(fisedevicelist.get(0));
			return response;
		}
		
		return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
	}

	@Override
	public Response delFiseDevice(QueryFiseDeviceParam param) {
		
		Response response=new Response();
		
		if(param.getFiseId()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		fiseDevicedao.deleteByPrimaryKey(param.getFiseId());
		return response.success();
		
	}

	@Override
	public Response updateFiseDevice(FiseDevice param) {

		Response response=new Response();
		
		if(param.getFiseId()==null){
			return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		if(!StringUtil.isEmpty(param.getIme())){
			//判断添加的设备的IME是否已经存在
			FiseDeviceExample exampleIME=new FiseDeviceExample();
			Criteria criteriaIME=exampleIME.createCriteria();
			criteriaIME.andImeEqualTo(param.getIme());
			List<FiseDevice> fisedevicelist=fiseDevicedao.selectByExample(exampleIME);
			System.out.println(param.getFiseId());
			System.out.println(fisedevicelist.get(0).getFiseId());
			System.out.println("-------------"+param.getFiseId()==fisedevicelist.get(0).getFiseId()+"-------------");
			if(fisedevicelist.size()!=0){
				if(param.getFiseId().equals(fisedevicelist.get(0).getFiseId())){
					
				}else{
					response.failure(ErrorCode.ERROR_FISE_DEVICE_IME_EXISTED);
					return response;
				}
			}
		}
		
		if(!StringUtil.isEmpty(param.getAccount())){
			//判断添加的设备的账号是否已经注册
			FiseDeviceExample exampleAccount=new FiseDeviceExample();
			Criteria criteriaAccount=exampleAccount.createCriteria();
			criteriaAccount.andAccountEqualTo(param.getAccount());
			List<FiseDevice> list=fiseDevicedao.selectByExample(exampleAccount);
			
			if(list.size()!=0){
				if(param.getFiseId().equals(list.get(0).getFiseId())){
					
				}else{
					response.failure(ErrorCode.ERROR_FISE_DEVICE_ACCOUNT_EXISTED);
					return response;
				}
			}
		}
		
		//更新设备相关信息
		long nowtime=System.currentTimeMillis() / 1000;
		param.setUpdated((int)nowtime);
		fiseDevicedao.updateByPrimaryKeySelective(param);
		response.success();
		return response;
		
	}

}
