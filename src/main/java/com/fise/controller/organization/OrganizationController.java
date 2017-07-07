package com.fise.controller.organization;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.entity.WiOrganization;
import com.fise.model.param.EventQueryParam;
import com.fise.server.organization.IOrganizationService;


@RestController
@RequestMapping("/boss/organization")
public class OrganizationController {
	
	private Logger logger=Logger.getLogger(getClass());
	
	@Resource
	IOrganizationService organtSvr;
	
	/*查询公司组织*/
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public Response queryOrgan(@RequestBody @Valid Map<String, Object> map){
		
		Response response=new Response();
		logger.info(map.toString());
		String name=(String) map.get("name");
		response=organtSvr.QueryOrganization(name);
		
		return response;
	}

	/*插入公司组织*/
    @RequestMapping(value="/insert",method=RequestMethod.POST)
    public Response insertOrgan(@RequestBody @Valid WiOrganization param){
        
        Response response=new Response();
        logger.info(param.toString());
        
        if(param.getName()==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        
        response=organtSvr.InsertOrganization(param);
        
        return response;
    }

    /*修改公司组织*/
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public Response updateOrgan(@RequestBody @Valid WiOrganization param){
     
     Response response=new Response();
     logger.info(param.toString());
     
     if(param.getId()==null){
         return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
     }
     
     response=organtSvr.UpdateOrganization(param);
     
     return response;
 }
    /*删除公司组织*/
    @RequestMapping(value="/del",method=RequestMethod.POST)
    public Response delOrgan(@RequestBody @Valid WiOrganization param){
        
       Response response=new Response();
       logger.info(param.toString());
       
       if(param.getId()==null){
           return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
       }
       
       response=organtSvr.delOrganization(param);
       
       return response;
    }
}
