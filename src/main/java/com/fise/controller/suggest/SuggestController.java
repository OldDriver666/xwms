package com.fise.controller.suggest;

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
import com.fise.model.entity.IMSuggest;
import com.fise.model.param.SuggestParam;
import com.fise.server.auth.IAuthService;
import com.fise.server.suggest.ISuggestService;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/suggest")
public class SuggestController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	ISuggestService iSuggestService;
	
	@Resource
	IAuthService authService;
	
	//添加suggest信息
	@IgnoreAuth
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Response addSuggest(@RequestBody @Valid IMSuggest record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		
		
		response=iSuggestService.insertSuggest(record);
		
		
		return response;
	}
	
	/**
	 * 查询suggest信息
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public Response querySuggest(@RequestBody @Valid Page<SuggestParam> param){
		
		Response response=new Response();
		logger.info(param.toString());
		response=iSuggestService.querySuggest(param);
		
		
		return response;
	}
	
	//查询suggest信息
	@IgnoreAuth
	@RequestMapping(value="/queryBySuggestId",method=RequestMethod.POST)
	public Response queryBySuggestId(@RequestBody @Valid Page<SuggestParam> param){
		
		Response response=new Response();
		logger.info(param.toString());
		response=iSuggestService.queryBySuggestId(param);
		
		
		return response;
	}
	
	//删除suggest信息
	@IgnoreAuth
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public Response delSuggest(@RequestBody @Valid SuggestParam param){
		
		Response response=new Response();
		
		
		logger.info(param.toString());
		
		if(param.getSuggestId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iSuggestService.delSuggest(param);
		
		
		return response;
	}
	
	//修改suggest信息
	@IgnoreAuth
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Response updateSuggest(@RequestBody @Valid IMSuggest record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		
		if(record.getId()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iSuggestService.updateSuggest(record);
		
		
		return response;
	}
}
