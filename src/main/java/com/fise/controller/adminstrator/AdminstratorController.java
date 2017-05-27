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
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.server.administrator.IAdministratorService;


@RestController
@RequestMapping("/admin")
public class AdminstratorController {

	private Logger logger = Logger.getLogger(getClass());
	
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
		resp = adminSvr.logout(param, request);
		
		return resp;
	}

}
