package com.fise.server.wiki.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.WikiMapper;
import com.fise.model.entity.Wiki;
import com.fise.model.entity.WikiExample;
import com.fise.model.entity.WikiExample.Criteria;
import com.fise.model.param.WikiParam;
import com.fise.server.wiki.IWikiService;

@Service
public class WikiServiceImpl implements IWikiService{

	
	@Autowired 
	WikiMapper wikiDao;
	
	@Override
	public Response insertWiki(Wiki record) {
		
		Response response=new Response();		
		wikiDao.insertSelective(record);
		response.success();
		return response;
	}

	@Override
	public Response queryWiki(WikiParam param) {
		
		Response response=new Response();
		
		WikiExample example=new WikiExample();
		Criteria criteria=example.createCriteria();
		
		if(param.getId()!=null){
			criteria.andIdEqualTo(param.getId());
		}
		if(param.getType()!=null){
			criteria.andTypeEqualTo(param.getType());
		}
		if(param.getTitle()!=null){
			criteria.andTitleLike(param.getTitle());
		}
		
		example.setOrderByClause("type");
		
		List<Wiki> list=wikiDao.selectByExample(example);
		if(list.size()==0){
			return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
		}
		
		response.success(list);
		return response;
	}	

	@Override
	public Response delWiki(WikiParam param) {
		
		Response response=new Response();
		WikiExample example=new WikiExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(param.getId());
		wikiDao.deleteByExample(example);
		
		return response.success();
	}

	@Override
	public Response updateWiki(Wiki record) {
		
		Response response=new Response();
		
		if(record.getId()==null){
		    return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
		}
		
		wikiDao.updateByPrimaryKeySelective(record);
		return response.success();
	}

}
