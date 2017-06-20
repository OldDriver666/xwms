package com.fise.controller.groupmessage;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.GroupMessageQueryParam;
import com.fise.server.groupmessage.IGroupMessageService;

@RestController
@RequestMapping("/boss")
public class GroupMessageController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IGroupMessageService iGroupMessageService;
    
    //查询群消息
    @RequestMapping(value="/groupmessage/query",method=RequestMethod.POST)
    public Response getGroupMessages(@RequestBody @Valid Page<GroupMessageQueryParam> param){
        
        Response response=new Response();
        
        logger.info(param.toString());
        response=iGroupMessageService.query(param);
        logger.info("end select groupmessage"+response.toString());
        
        return response;
    }
}
