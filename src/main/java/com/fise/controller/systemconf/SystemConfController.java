package com.fise.controller.systemconf;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.entity.IMSystemConf;
import com.fise.model.param.SystemConfParam;
import com.fise.server.systemconf.ISystemConfService;

@RestController
@RequestMapping("/boss/systemconf")
public class SystemConfController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	ISystemConfService iSystemConfService;
	
	/*添加systemconf信息*/
	@RequestMapping(value="/addsystemconf",method=RequestMethod.POST)
	public Response addSystemConf(@RequestBody @Valid IMSystemConf record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		response=iSystemConfService.insertSystemConf(record);
		logger.info("end insert systemconf"+response.toString());
		
		return response;
	}
	
	/*查询systemconf信息*/
	@RequestMapping(value="/querysystemconf",method=RequestMethod.POST)
	public Response querySystemConf(@RequestBody @Valid SystemConfParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=iSystemConfService.querySystemConf(param);
		logger.info("end select systemconf"+response.toString());
		
		return response;
	}
	
	/*删除systemconf信息*/
	@RequestMapping(value="/delsystemconf",method=RequestMethod.POST)
	public Response delSystemConf(@RequestBody @Valid SystemConfParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=iSystemConfService.delSystemConf(param);
		logger.info("end delete systemconf"+response.toString());
		
		return response;
	}
	
	/*修改systemconf信息*/
	@RequestMapping(value="/updatesystemconf",method=RequestMethod.POST)
	public Response updateSystemConf(@RequestBody @Valid IMSystemConf record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		response=iSystemConfService.updateSystemConf(record);
		logger.info("end update systemconf"+response.toString());
		
		return response;
	}
}
