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
import com.fise.model.param.RolePermissionParam;
import com.fise.server.role.IRoleService;

@RestController
@RequestMapping("/boss/role")
public class RoleController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource IRoleService roleSvr;
    
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Response query(@RequestBody Map<String, Object> param){
        Response resp = new Response();
        logger.debug(param.toString());
        Integer amdinRole = (Integer)param.get("role_id");
        Integer amdinOrgid = (Integer)param.get("organ_id");
        if(amdinRole == null || amdinOrgid == null){
            resp.failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
        } else {
            resp = roleSvr.queryAll(amdinRole, amdinOrgid);
        }

        return resp;
    }

    @RequestMapping(value = "/queryAuth", method = RequestMethod.POST)
    public Response queryAuth(@RequestBody Map<String, Object> param){
        Response resp = new Response();
        logger.debug(param.toString());
        Integer amdinRole = (Integer)param.get("role_id");
        Integer amdinOrgid = (Integer)param.get("organ_id");
        if(amdinRole == null || amdinOrgid == null){
            resp.failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
        } else {
            resp = roleSvr.queryRoleAuth(amdinRole, amdinOrgid);
        }

        return resp;
    }
    
    @RequestMapping(value = "/updateAuth", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid RolePermissionParam param){
        Response resp = new Response();
        logger.debug(param);
        if(param.getPermisList().size() > 0){
            resp = roleSvr.updateRoleAuth(param);
        } else {
            resp.failure(ErrorCode.ERROR_PARAM_NOT_VALID_EXCEPTION);
            resp.setMsg("更新权限列表为空");
        }
        return resp;
    }
}
