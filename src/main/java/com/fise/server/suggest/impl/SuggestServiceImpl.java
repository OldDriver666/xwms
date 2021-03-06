package com.fise.server.suggest.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.IMSuggestMapper;
import com.fise.model.entity.IMSuggest;
import com.fise.model.entity.IMSuggestExample;
import com.fise.model.entity.IMSuggestExample.Criteria;
import com.fise.model.param.SuggestParam;
import com.fise.server.suggest.ISuggestService;
import com.fise.utils.StringUtil;

@Service
public class SuggestServiceImpl implements ISuggestService{

	
	@Autowired 
	IMSuggestMapper IMSuggestDao;
	
	@Override
	public Response insertSuggest(IMSuggest record) {
		
		Response response=new Response();
		
		//更新数据
		long nowtime=System.currentTimeMillis()/1000;
		record.setCreated((int)nowtime);
		record.setUpdated((int)nowtime);
		if (StringUtil.isEmpty(record.getSuggestId())) {
			record.setSuggestId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		
		IMSuggestDao.insertSelective(record);
		response.success();
		return response;
	}

	@Override
	public Response querySuggest(Page<SuggestParam> param) {
		
		Response response=new Response();
		SuggestParam suggestParam = param.getParam();
		IMSuggestExample example=new IMSuggestExample();
		Criteria criteria=example.createCriteria();
		criteria.andTitleIsNotNull();
		criteria.andTitleNotEqualTo("");
		if(suggestParam.getUserId()!=null){
			criteria.andUserIdEqualTo(suggestParam.getUserId());
		}
		if(StringUtil.isNotEmpty(suggestParam.getUname())){
			criteria.andUnameLike("%" + suggestParam.getUname() + "%");
		}
		if(StringUtil.isNotEmpty(suggestParam.getTitle())){
			criteria.andTitleLike("%" + suggestParam.getTitle() + "%");
		}
		if(suggestParam.getType()!=null){
			criteria.andTypeEqualTo(suggestParam.getType());
		}
		if(suggestParam.getStatus()!=null){
			criteria.andStatusEqualTo(suggestParam.getStatus());
		}
		example.setOrderByClause("created desc");
		
		List<IMSuggest> list=IMSuggestDao.selectByExamplebypage(example, param);
		if(list.size()==0){
			return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
		}
		
		Page<IMSuggest> page=new Page<IMSuggest>();
		page.setPageNo(param.getCurrentPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalCount(param.getTotalCount());
		page.setTotalPageCount(param.getTotalPageCount());
		page.setResult(list);
		
		response.success(page);
		return response;
	}
	
	@Override
	public Response queryBySuggestId(Page<SuggestParam> param) {
		
		Response response=new Response();
		
		IMSuggestExample example=new IMSuggestExample();
		Criteria criteria=example.createCriteria();
		
		criteria.andSuggestIdEqualTo(param.getParam().getSuggestId());
		example.setOrderByClause("created");
		
		List<IMSuggest> list=IMSuggestDao.selectByExample(example);
		if(list.size()==0){
			return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
		}
		
		Page<IMSuggest> page=new Page<IMSuggest>();
		page.setPageNo(param.getCurrentPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalCount(param.getTotalCount());
		page.setTotalPageCount(param.getTotalPageCount());
		page.setResult(list);
		
		response.success(page);
		return response;
	}

	@Override
	public Response delSuggest(SuggestParam param) {
		
		Response response=new Response();
		IMSuggestExample example=new IMSuggestExample();
		Criteria criteria=example.createCriteria();
		
		if(StringUtil.isNotEmpty(param.getSuggestId())){
			criteria.andSuggestIdEqualTo(param.getSuggestId());
		}
		if(param.getId()!=null){
			criteria.andIdEqualTo(param.getId());
		}
		
		IMSuggestDao.deleteByExample(example);
		
		return response.success();
	}

	@Override
	public Response updateSuggest(IMSuggest record) {
		
		Response response=new Response();
		IMSuggest suggest = new IMSuggest();
		IMSuggestExample example=new IMSuggestExample();
		Criteria criteria=example.createCriteria();
		criteria.andSuggestIdEqualTo(record.getSuggestId());
		long updatetime=System.currentTimeMillis()/1000;
		suggest.setType(record.getType());
		suggest.setStatus(record.getStatus());
		suggest.setUpdated((int)updatetime);
		IMSuggestDao.updateByExampleSelective(suggest, example);
		return response.success();
	}

}
