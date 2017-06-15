package com.fise.server.suggest.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.IMSuggestMapper;
import com.fise.model.entity.IMSuggest;
import com.fise.model.entity.IMSuggestExample;
import com.fise.model.entity.IMSuggestExample.Criteria;
import com.fise.model.param.SuggestParam;
import com.fise.server.suggest.ISuggestService;
import com.fise.utils.StringUtil;

@Service
public class ISuggestServiceImpl implements ISuggestService{

	private Logger logger=Logger.getLogger(getClass());
	
	@Autowired 
	IMSuggestMapper IMSuggestDao;
	
	@Override
	public Response insertSuggest(IMSuggest record) {
		
		Response response=new Response();
		
		//判断user_id是否已经存在
		IMSuggestExample example=new IMSuggestExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserIdEqualTo(record.getUserId());
		List<IMSuggest> list=IMSuggestDao.selectByExample(example);
		
		if(list.size()!=0){
			response.failure(ErrorCode.ERROR_SUGGEST_USER_ID_EXISTED);
			return response;
		}	
		
		//更新数据
		long nowtime=System.currentTimeMillis()/1000;
		record.setCreated((int)nowtime);
		record.setUpdated((int)nowtime);
		
		IMSuggestDao.insertSelective(record);
		response.success();
		return response;
	}

	@Override
	public Response querySuggest(SuggestParam param) {
		
		Response response=new Response();
		
		IMSuggestExample example=new IMSuggestExample();
		Criteria criteria=example.createCriteria();
		
		if(param.getUserId()!=null){
			criteria.andUserIdEqualTo(param.getUserId());
		}
		
		if(param.getUname()!=null){
			criteria.andUnameEqualTo(param.getUname());
		}
		
		List<IMSuggest> list=IMSuggestDao.selectByExample(example);
		if(list.size()==0){
			return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
		}
		
		response.success(list);
		return response;
	}

	@Override
	public Response delSuggest(SuggestParam param) {
		
		Response response=new Response();
		
		IMSuggestDao.deleteByPrimaryKey(param.getId());
		
		return response.success();
	}

	@Override
	public Response updateSuggest(IMSuggest record) {
		
		Response response=new Response();
		
		if(record.getUserId()!=null){
			IMSuggestExample example=new IMSuggestExample();
			Criteria criteria=example.createCriteria();
			criteria.andUserIdEqualTo(record.getUserId());
			List<IMSuggest> list=IMSuggestDao.selectByExample(example);
			
			if(list.size()!=0){
				if(record.getId().equals(list.get(0).getId())){
					
				}else{
					return response.failure(ErrorCode.ERROR_SUGGEST_USER_ID_EXISTED);
				}
			}	
		}
		
		//更新数据
		long updatetime=System.currentTimeMillis()/1000;
		record.setUpdated((int)updatetime);
		IMSuggestDao.updateByPrimaryKeySelective(record);
		return response.success();
	}

}
