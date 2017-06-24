package com.fise.controller.queryuser;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.QueryUserParam;
import com.fise.server.queryuser.IQueryUserService;

@RestController
@RequestMapping("/boss/user")
public class QueryUserController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IQueryUserService IQueryUserService;
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    public Response queryUserInfo(@RequestBody @Valid QueryUserParam param){
        
        Response response=new Response();

        logger.info(param.toString());
        response=IQueryUserService.queryUser(param);
        logger.info("end query user"+response.toString());
        
        return response;
    }
}
