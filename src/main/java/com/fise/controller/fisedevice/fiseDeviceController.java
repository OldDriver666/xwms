package com.fise.controller.fisedevice;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.entity.FiseDevice;
import com.fise.model.param.QueryFiseDeviceParam;
import com.fise.server.fisedevice.FiseDeviceService;

@RestController
@RequestMapping("/boss")
public class fiseDeviceController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	FiseDeviceService fiseDeviceService;
	
	/*添加fise设备*/
	@RequestMapping(value="/addfisedevice",method=RequestMethod.POST)
	public Response addFiseDevice(@RequestBody @Valid FiseDevice param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=fiseDeviceService.insertFiseDevice(param);
		logger.info("end insert fisedevice"+response.toString());
		
		return response;
	}
	
	/*查询fise设备     可以通过id  设备IME号    账号account*/
	@RequestMapping(value="/queryfisedevice",method=RequestMethod.POST)
	public Response queryFiseDevice(@RequestBody @Valid QueryFiseDeviceParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=fiseDeviceService.queryFiseDevice(param);
		logger.info("end query fisedevice"+response.toString());
		
		return response;
	}
	
	/*删除fise设备*/
	@RequestMapping(value="/delfisedevice",method=RequestMethod.POST)
	public Response delFiseDevice(@RequestBody @Valid QueryFiseDeviceParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=fiseDeviceService.delFiseDevice(param);
		logger.info("end delete fisedevice"+response.toString());
		
		return response;
	}
	
	/*修改fise设备信息*/
	@RequestMapping(value="/updatefisedevice",method=RequestMethod.POST)
	public Response updateFiseDevice(@RequestBody @Valid FiseDevice param){

		Response response=new Response();
		
		logger.info(param.toString());
		response=fiseDeviceService.updateFiseDevice(param);
		logger.info("end update fisedevice"+response.toString());
		
		return response;
	}
}
