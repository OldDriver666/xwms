package com.fise.controller.vediorecord;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.QueryVedioRecordParam;
import com.fise.server.vediorecord.IVedioRecordService;




@RestController
@RequestMapping("/boss/videorecord")
public class VedioRecordController {
    
    private Logger logger=Logger.getLogger(getClass());
    
    @Resource
    IVedioRecordService videoRecordService;
    
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    public Response queryVideoRecordInfo(@RequestBody @Valid Page<QueryVedioRecordParam> param){
        
        Response response=new Response();
        logger.info(param.toString());
        response = videoRecordService.queryVideoRecordByPage(param);
       
        
        return response;
    }
    

}
