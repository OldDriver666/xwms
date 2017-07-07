package com.fise.controller.adminstrator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.param.AdminInsert;
import com.fise.model.param.AdminQuery;
import com.fise.model.param.AdminUpdate;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.server.administrator.IAdministratorService;

@RestController
@RequestMapping("/boss/admin")
public class AdminstratorController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private IAdministratorService adminSvr;
	
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response adminLogin(@RequestBody @Valid LoginParam param)
	{
		Response resp = new Response();
		try {
			logger.info(param.toString());
			resp = adminSvr.login(param);
			logger.info("end login resp=" + resp.toString());
		} catch (Exception e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_SYSTEM);
		}
		return resp;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Response logout(@RequestBody @Valid LogoutParam param, HttpServletRequest request) {
		Response resp = new Response();
		logger.info(param.toString());
		resp = adminSvr.logout(param, request);
		
		return resp;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Response adminInsert(@RequestBody @Valid AdminInsert param) {
		Response resp = new Response();
		logger.info(param.toString());
		resp = adminSvr.insertAdmin(param);
		return resp;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response adminUpdate(@RequestBody @Valid AdminUpdate param) {
		Response resp = new Response();
		logger.info(param.toString());
		resp = adminSvr.updateAdmin(param);
		return resp;
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public Response adminQuery(@RequestBody @Valid AdminQuery param){
	    Response resp = new Response();
	    logger.info(param.toString());
	    resp = adminSvr.queryAdmin(param);
	    return resp;
	}
}
