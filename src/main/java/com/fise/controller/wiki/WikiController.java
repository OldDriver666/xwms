package com.fise.controller.wiki;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.Wiki;
import com.fise.model.param.WikiParam;
import com.fise.server.auth.IAuthService;
import com.fise.server.wiki.IWikiService;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/wiki")
public class WikiController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	IWikiService iWikiService;
	
	@Resource
	IAuthService authService;
	
	//添加wiki信息
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Response addWiki(@RequestBody @Valid Wiki record){
		
		Response response=new Response();
		
		if(!authService.inserAuth()){
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
		
		logger.info(record.toString());
		
		if(StringUtil.isEmpty(record.getTitle())){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iWikiService.insertWiki(record);
		
		
		return response;
	}
	
	//查询wiki信息
	@IgnoreAuth
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public Response queryWiki(@RequestBody @Valid WikiParam param){
		
		Response response=new Response();
		logger.info(param.toString());
		response=iWikiService.queryWiki(param);
		
		
		return response;
	}
	
	//删除wiki信息
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public Response delWiki(@RequestBody @Valid WikiParam param){
		
		Response response=new Response();
		
		if(!authService.updateAuth()){
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
		
		logger.info(param.toString());
		
		if(param.getId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iWikiService.delWiki(param);
		
		
		return response;
	}
	
	//修改 信息
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Response updateWiki(@RequestBody @Valid Wiki record){
		
		Response response=new Response();
		
		if(!authService.updateAuth()){
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
		
		logger.info(record.toString());
		
		if(record.getId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iWikiService.updateWiki(record);
		
		
		return response;
	}
}
