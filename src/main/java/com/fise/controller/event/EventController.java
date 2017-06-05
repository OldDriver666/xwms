package com.fise.controller.event;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.EventQueryParam;
import com.fise.server.device.IEventService;

@RestController
@RequestMapping("/boss")
public class EventController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	IEventService eventSvr;
	
	/*查询设备事件*/
	@RequestMapping(value="/event/query",method=RequestMethod.POST)
	public Response queryEvent(@RequestBody @Valid EventQueryParam param){
		
		Response response=new Response();
		
		logger.info(param.toString());
		response=eventSvr.query(param);
		
		return response;
	}

}
