package com.fise.controller.sessionmessage;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.SessionMessageQueryParam;
import com.fise.server.sessionmessage.ISessionMessageService;

@RestController
@RequestMapping("/boss")
public class SessionMessageController {
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    ISessionMessageService iSessionMessageService;
    
    @RequestMapping(value="/sessionmessage/query",method=RequestMethod.POST)
    public Response getSessionMessage(@RequestBody @Valid SessionMessageQueryParam param){
        
        Response response=new Response();
        logger.info(param.toString());
        response=iSessionMessageService.query(param);
        
        
        return response;
    }
}
