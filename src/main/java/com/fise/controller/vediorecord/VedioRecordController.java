package com.fise.controller.vediorecord;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
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
    
    /**
     * 查询设备多媒体信息
     * @param param
     * @return
     */
    @RequestMapping(value="/query",method=RequestMethod.POST)
    public Response queryVideoRecordInfo(@RequestBody @Valid Page<QueryVedioRecordParam> param){
        
        Response response=new Response();
        logger.info(param.toString());
        try {
        	response = videoRecordService.queryVideoRecordByPage(param);
		} catch (Exception e) {
			 e.printStackTrace();
			 response.failure(ErrorCode.ERROR_SYSTEM);
		}
        return response;
    }
    
    /**
     * 查询设备数量和多媒体数量
     */
    @RequestMapping(value="/queryCount",method=RequestMethod.POST)
    public Response queryVideoRecordCount(@RequestBody QueryVedioRecordParam param){
        
        Response response=new Response();
        logger.info(param);
        try {
        	response = videoRecordService.queryVideoRecordCount(param.getCompanyId());
		} catch (Exception e) {
			 e.printStackTrace();
			 response.failure(ErrorCode.ERROR_SYSTEM);
		}
        return response;
    }
    

}
