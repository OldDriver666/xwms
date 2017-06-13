package com.fise.controller.clienttype;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.entity.IMClientType;
import com.fise.model.param.ClientTypeParam;
import com.fise.server.clienttype.IClientTypeService;

@RestController
@RequestMapping("/boss/clienttype")
public class ClientTypeController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	IClientTypeService imClientTypeService;
	
	/*添加clienttype*/
	@RequestMapping(value="/addclienttype",method=RequestMethod.POST)
	public Response addClientType(@RequestBody @Valid IMClientType param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=imClientTypeService.insertClientType(param);
		logger.info("end insert IMClientType "+response.toString());
		
		return response;
	}
	
	/*查询clienttype设备   */
	@RequestMapping(value="/queryclienttype",method=RequestMethod.POST)
	public Response queryclienttype(@RequestBody @Valid ClientTypeParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=imClientTypeService.queryClientType(param);
		logger.info("end query IMClientType"+response.toString());
		
		return response;
	}
	
	/*删除fise设备*/
	@RequestMapping(value="/delclienttype",method=RequestMethod.POST)
	public Response delFiseDevice(@RequestBody @Valid ClientTypeParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=imClientTypeService.delClientType(param);
		logger.info("end delete IMClientType"+response.toString());
		
		return response;
	}
	
	/*修改fise设备信息*/
	@RequestMapping(value="/updateclienttype",method=RequestMethod.POST)
	public Response updateFiseDevice(@RequestBody @Valid IMClientType record){

		Response response=new Response();
		
		logger.info(record.toString());
		response=imClientTypeService.updateClientType(record);
		logger.info("end update IMClientType"+response.toString());
		
		return response;
	}
}
