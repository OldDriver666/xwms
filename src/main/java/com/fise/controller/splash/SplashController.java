package com.fise.controller.splash;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.entity.IMSplash;
import com.fise.model.param.SplashParam;
import com.fise.server.splash.ISplashService;

@RestController
@RequestMapping("/boss/splash")
public class SplashController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	ISplashService iSplashService;
	
	//添加闪屏信息
	@RequestMapping(value="/addsplash",method=RequestMethod.POST)
	public Response addSplash(@RequestBody @Valid IMSplash record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		response=iSplashService.insertSplash(record);
		logger.info("end insert imsplash"+response.toString());
		
		return response;
	}
	
	//查询闪屏信息
	@RequestMapping(value="/querysplash",method=RequestMethod.POST)
	public Response querySplash(@RequestBody @Valid SplashParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=iSplashService.querySplash(param);
		logger.info("end select imsplash"+response.toString());
		
		return response;
	}
	
	//删除闪屏信息
	@RequestMapping(value="/delsplash",method=RequestMethod.POST)
	public Response delSplash(@RequestBody @Valid SplashParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=iSplashService.delSplash(param);
		logger.info("end delete imsplash"+response.toString());
		
		return response;
	}
	
	//修改闪屏信息
	@RequestMapping(value="/updatesplash",method=RequestMethod.POST)
	public Response updateSplash(@RequestBody @Valid IMSplash record){
			
		Response response=new Response();
			
		logger.info(record.toString());
		response=iSplashService.updateSplash(record);
		logger.info("end update imsplash"+response.toString());
			
		return response;
	}
}
