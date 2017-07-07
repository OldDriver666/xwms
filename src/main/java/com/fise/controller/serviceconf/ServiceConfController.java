package com.fise.controller.serviceconf;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.entity.IMServiceConf;
import com.fise.model.param.ServiceConfParam;
import com.fise.server.serviceconf.IServiceConfService;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/serviceconf")
public class ServiceConfController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	IServiceConfService iServiceConfService;
	
	/*添加serviceconf信息*/
	@RequestMapping(value="/addserviceconf",method=RequestMethod.POST)
	public Response addServiceConf(@RequestBody @Valid IMServiceConf record){
		
		Response response=new Response();
		logger.info(record.toString());
		
		if(StringUtil.isEmpty(record.getServiceName())||StringUtil.isEmpty(record.getServicePwd())){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iServiceConfService.insertServiceConf(record);
		logger.info("end insert imserviceconf"+response.toString());
		
		return response;
	}
	
	/*查询serviceconf信息*/
	@RequestMapping(value="/queryserviceconf",method=RequestMethod.POST)
	public Response queryServiceConf(@RequestBody @Valid ServiceConfParam param){
		
		Response response=new Response();
		logger.info(param.toString());
		
		if(StringUtil.isEmpty(param.getServiceName()) || StringUtil.isEmpty(param.getServicePwd())){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
	
		response=iServiceConfService.selectServiceConf(param);
		logger.info("end select imserviceconf"+response.toString());
		
		return response;
	}
	
	/*删除serviceconf信息*/
	@RequestMapping(value="/delserviceconf",method=RequestMethod.POST)
	public Response delServiceConf(@RequestBody @Valid ServiceConfParam param){
		
		Response response=new Response();
		logger.info(param.toString());
		
		if(param.getConfigid()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iServiceConfService.delServiceConf(param);
		logger.info("end delete imserviceconf"+response.toString());
		
		return response;
	}
	
	/*修改serviceconf信息*/
	@RequestMapping(value="/updateserviceconf",method=RequestMethod.POST)
	public Response updateServiceConf(@RequestBody @Valid IMServiceConf record){
		
		Response response=new Response();
		logger.info(record.toString());
		
		if(record.getConfigid()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
		
		response=iServiceConfService.updateServiceConf(record);
		logger.info("end update imserviceconf"+response.toString());
		
		return response;
	}
}
