package com.fise.server.administrator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fise.base.Response;
import com.fise.model.entity.WiAdmin;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;

public interface IAdministratorService {
	/* 用户登录 */
	public Response login(LoginParam param);
	
	/* 用户退出 */
	public Response logout(LogoutParam param, HttpServletRequest request);
	
	/* 查询公司所有管理员 */
	public List<WiAdmin> queryAdminByCompanyId(Integer adminId, Integer companyId);
	
}
