package com.fise.controller.role;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.param.InsertAuthParam;
import com.fise.model.param.InsertRoleAndAuthsParam;
import com.fise.model.param.InsertRoleParam;
import com.fise.model.param.QueryRoleParam;
import com.fise.model.param.RolePermissionParam;
import com.fise.model.param.UpdateRoleAndAuthsParam;
import com.fise.model.result.ModulePermissResult;
import com.fise.server.auth.IAuthService;
import com.fise.server.role.IRoleService;
import com.fise.utils.AdminUtil;
import com.fise.utils.Constants;

@RestController
@RequestMapping("/boss/role")
public class RoleController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    IRoleService roleSvr;

    @Resource
    IAuthService authService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Response queryRole(@RequestBody @Valid QueryRoleParam param) {
        Response resp = new Response();
        param.setCreator_id(HttpContext.getMemberId());
        param.setCompany_id(HttpContext.getCompanyId());
        List<WiOrganizationRole> data = roleSvr.queryRole(param);
        resp.success(data);
        logger.info("查询角色:" + param.toString() + " 结果:" + resp.getMsg());
        return resp;
    }

    @RequestMapping(value = "/queryAuth", method = RequestMethod.POST)
    public Response queryAuth(@RequestBody @Valid QueryRoleParam param) {
        Response resp = new Response();
        resp = roleSvr.queryRoleAuth(param);
        return resp;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid WiOrganizationRole param) {
        Response resp = new Response();
        
        if (!authService.updateAuth()) {
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        
        if (param.getId() == null) {
            return resp.failure(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
        }
        resp = roleSvr.updateRole(param);
        return resp;
    }

    @RequestMapping(value = "/updateAuth", method = RequestMethod.POST)
    public Response updateAuth(@RequestBody @Valid RolePermissionParam param) {
        Response resp = new Response();

        if (!authService.updateAuth()) {
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }

        resp = roleSvr.updateRoleAuth(param);
        return resp;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insertRole(@RequestBody @Valid InsertRoleParam role) {
        Response response = new Response();

        if (!authService.inserAuth()) {
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        
        response = roleSvr.insertRole(role);
        logger.info("新增角色:" + role.toString() + " 结果:" + response.getMsg());
        return response;
    }

    @RequestMapping(value = "/insertAuth", method = RequestMethod.POST)
    public Response insertAuth(@RequestBody @Valid InsertAuthParam role) {
        Response response = new Response();

        if (!authService.inserAuth()) {
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        try {
        	response = roleSvr.insertAuth(role);
		} catch (Exception e) {
			logger.info("新增权限失败");
		}
        logger.info("新增权限:" + role.toString() + " 结果:" + response.getMsg());
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response deleteRole(@RequestBody @Valid WiOrganizationRole role) {
        Response response = new Response();

        if (!authService.inserAuth()) {
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        logger.info(role.toString());

        if (role.getId() == null) {
            return response.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        response = roleSvr.delRole(role);
        return response;
    }
    
    @RequestMapping(value = "/insertRoleAndAuths", method = RequestMethod.POST)
    public Response insertRoleAndAuths(@RequestBody @Valid InsertRoleAndAuthsParam param) {
        Response response = new Response();

        if (!authService.inserAuth()) {
            return response.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        param.getRole().setCreatorId(HttpContext.getMemberId());
        response = roleSvr.insertRoleAndAuths(param.getRole(), param.getAuths());
        logger.info("新增角色:" + param.toString() + " 结果:" + response.getMsg());
        return response;
    }
    
    @RequestMapping(value = "/updateRoleAndAuths", method = RequestMethod.POST)
    public Response updateRoleAndAuths(@RequestBody @Valid UpdateRoleAndAuthsParam param) {
        Response resp = new Response();
        param.getRole().setCreatorId(HttpContext.getMemberId());
        if (!authService.updateAuth()) {
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }
        param.getRole().setCreatorId(HttpContext.getMemberId());
        resp = roleSvr.updateRoleAndAuths(param.getRole(), param.getAuths());
        return resp;
    }
    
    
    @RequestMapping(value = "/deleteRoleAndAuths", method = RequestMethod.POST)
    public Response deleteRoleAndAuths(@RequestBody  @Valid InsertAuthParam role) {
        Response resp = new Response();
        role.setCreatorId(HttpContext.getMemberId());
        if (!authService.updateAuth()) {
            return resp.failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
        }

        resp = roleSvr.deleteRoleAndAuths(role);
        return resp;
    }
    
    /**
     * 查询病人管理系统权限
     * @param param
     * @return
     */
    @RequestMapping(value = "/queryPatientAuth", method = RequestMethod.POST)
    public Response queryPatientAuth(@RequestBody @Valid QueryRoleParam param) {
        Response resp = new Response();
        resp = roleSvr.queryPatientAuth(param);
        return resp;
    }
    
    @RequestMapping(value = "/queryRoleByPage", method = RequestMethod.POST)
    public Response queryWiOrganizationRoleByPage(@RequestBody @Valid Page<WiOrganizationRole> page) {
        Response resp = new Response();
        page.getParam().setCreatorId(HttpContext.getMemberId());
        logger.info(page.toString());
        resp = roleSvr.queryOrganizationRoleByPage(page);
        return resp;
    }
    
}
