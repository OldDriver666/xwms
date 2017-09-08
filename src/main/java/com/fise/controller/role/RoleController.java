package com.fise.controller.role;

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
import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.param.RolePermissionParam;
import com.fise.server.auth.IAuthService;
import com.fise.server.role.IRoleService;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/boss/role")
public class RoleController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource 
    IRoleService roleSvr;
    
    @Resource
    IAuthService authService;
    
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Response query(@RequestBody Map<String, Object> param){
        Response resp = new Response();
        
        logger.info(param.toString());
        
        Integer amdinRole = (Integer)param.get("role_id");
        Integer amdinOrgid = (Integer)param.get("company_id");
        Integer departId = (Integer)param.get("depart_id");
        if(amdinRole == null || amdinOrgid == null || departId == null){
            resp.failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
        } else {
            resp = roleSvr.queryAll(amdinRole, amdinOrgid, departId);
        }

        return resp;
    }

    @RequestMapping(value = "/queryAuth", method = RequestMethod.POST)
    public Response queryAuth(@RequestBody Map<String, Object> param){
        Response resp = new Response();
        
        logger.info(param.toString());
        Integer amdinRole = (Integer)param.get("role_id");
        Integer amdinOrgid = (Integer)param.get("organ_id");
        if(amdinRole == null || amdinOrgid == null){
            resp.failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
        } else {
            resp = roleSvr.queryRoleAuth(amdinRole, amdinOrgid);
        }

        return resp;
    }
    
    @RequestMapping(value = "/allAuth", method = RequestMethod.POST)
    public Response queryAllAuth(@RequestBody Map<String, Object> param){
        Response resp = new Response();
        logger.info(param.toString());
        
        Integer amdinRole = (Integer)param.get("role_id");
        Integer amdinOrgid = (Integer)param.get("organ_id");
        if(amdinRole == null || amdinOrgid == null){
            resp.failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
        } else {
            resp = roleSvr.queryRoleAuthForUpdate(amdinRole, amdinOrgid);
        }

        return resp;
    }
    
    @RequestMapping(value = "/updateAuth", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid RolePermissionParam param){
        Response resp = new Response();
        
        if(!authService.updateAuth()){
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        logger.info(param.toString());
        
        if(param.getPermisList().size() > 0){
            resp = roleSvr.updateRoleAuth(param);
        } else {
            resp.failure(ErrorCode.ERROR_PARAM_NOT_VALID_EXCEPTION);
            resp.setMsg("更新权限列表为空");
        }
        return resp;
    }
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public Response add(@RequestBody @Valid WiOrganizationRole role){
        Response response=new Response();
        
        if(!authService.inserAuth()){
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        logger.info(role.toString());
        
        if(role.getAuthLevel()==null || StringUtil.isEmpty(role.getName())){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        
        response=roleSvr.addRole(role);
        
        return response;
    }
    
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public Response delete(@RequestBody @Valid WiOrganizationRole role){
        Response response=new Response();
        
        if(!authService.inserAuth()){
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        logger.info(role.toString());
        
        if(role.getId() ==null){
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        response=roleSvr.delRole(role);
        return response;
    }
}
