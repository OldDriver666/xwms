package com.fise.controller.deviceversion;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.entity.IMDevcieVersion;
import com.fise.model.param.DeviceVersionParam;
import com.fise.server.deviceversion.IDeviceVersionService;

@RestController
@RequestMapping("/admin")
public class DeviceVersionController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	IDeviceVersionService iDeviceVersionService;
	
	/*添加设备版本信息*/
	@RequestMapping(value="/adddeviceversion",method=RequestMethod.POST)
	public Response addDeviceVersion(@RequestBody @Valid IMDevcieVersion record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		response=iDeviceVersionService.insertDeviceVersion(record);
		logger.info("end insert deviceversion"+response.toString());
		
		return response;
	}
	
	/*查询设备版本信息*/
	@RequestMapping(value="/querydeviceversion",method=RequestMethod.POST)
	public Response queryDeviceVersion(@RequestBody @Valid DeviceVersionParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=iDeviceVersionService.queryDeviceVersion(param);
		logger.info("end select deviceversion"+response.toString());
		
		return response;
	}
	
	/*删除设备版本信息*/
	@RequestMapping(value="/deldeviceversion",method=RequestMethod.POST)
	public Response delDeviceVersion(@RequestBody @Valid DeviceVersionParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=iDeviceVersionService.delDeviceVersion(param);
		logger.info("end delete deviceversion"+response.toString());
		
		return response;
	}
	
	/*修改设备版本信息*/
	@RequestMapping(value="/updatedeviceversion",method=RequestMethod.POST)
	public Response updateDeviceVersion(@RequestBody @Valid IMDevcieVersion record){
		
		Response response=new Response();
		
		logger.info(record.toString());
		response=iDeviceVersionService.updateDeviceVersion(record);
		logger.info("end update deviceversion"+response.toString());
		
		return response;
	}
}
