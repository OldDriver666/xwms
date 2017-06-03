package com.fise.server.administrator.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.WiAdminMapper;
import com.fise.framework.redis.RedisManager;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiAdminExample;
import com.fise.model.entity.WiAdminExample.Criteria;
import com.fise.model.param.AdminInsert;
import com.fise.model.param.AdminUpdate;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.server.administrator.IAdministratorService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;

import redis.clients.jedis.Jedis;


@Service
public class AdministratorServiceImpl implements IAdministratorService {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private WiAdminMapper adminDao;
	
	
	@Override
	public Response login(LoginParam param) {
		
		Response resp = new Response();

		WiAdminExample example = new WiAdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(param.getAccount());
		List<WiAdmin> adminList = adminDao.selectByExample(example);
		if (adminList.size() == 0) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		WiAdmin admin = adminList.get(0);
		if (!param.getPassword().equals(admin.getPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		
		resp = login(admin);
		return resp;

	}

	private Response login(WiAdmin admin)
	{
		Response resp = new Response();
		
		Integer adminId = admin.getId();
		String accessToken = genAccessToken(adminId);
		WiAdmin updateAdmin = new WiAdmin();
		Integer nowTime = DateUtil.getLinuxTimeStamp();
		updateAdmin.setAccessToken(accessToken);
		updateAdmin.setId(adminId);
		updateAdmin.setLastLogin(nowTime);
		
		adminDao.updateByPrimaryKeySelective(updateAdmin);
		admin.setAccessToken(accessToken);
		admin.setLastLogin(nowTime);
		admin.setPassword("");
		admin.setSalt("");
		resp.success(admin);
		return resp;
	}

	// 生成access token
	private String genAccessToken(Integer memberId) {
		Jedis jedis = null;
		String accessToken = null;
		try {
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
			accessToken = CommonUtil.getAccessToken();
			String key = Constants.REDIS_KEY_PREFIX_MEMBER_ACCESS_TOKEN + accessToken;
			String value = memberId + "";
			jedis.setex(key, Constants.ACCESS_TOKEN_EXPIRE_SECONDS, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		return accessToken;
	}
	// 删除access token 
	private boolean delAccessToken(Integer memberId, String accessToken) {
		boolean ret = false;
		Jedis jedis = null;
		try {
			
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
			
			String key = Constants.REDIS_KEY_PREFIX_MEMBER_ACCESS_TOKEN + accessToken;
			
			String gymIdInRedis = jedis.get(key);
			logger.debug("logout will delete token=" + key + "redis id=" + gymIdInRedis + " memberId=" + memberId);
			if (!memberId.toString().equals(gymIdInRedis)) return false;
			jedis.del(key);
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		return ret;
	}

	
	@Override
	public Response logout(LogoutParam param, HttpServletRequest request) {
		Response resp = new Response();;
		Integer memberId = param.getAdminId();
		String accessToken = request.getHeader(Constants.HEADER_FIELD_NAME_ACCESS_TOKEN);
		try {
			
			delAccessToken(memberId, accessToken);
			resp.success();

			//exit clear accessToken 
			WiAdmin updateAdmin = new WiAdmin();
			long nowTime = System.currentTimeMillis() / 1000;
			updateAdmin.setAccessToken("");
			updateAdmin.setId(memberId);
			updateAdmin.setUpdated((int) nowTime);
			
			adminDao.updateByPrimaryKeySelective(updateAdmin);
		} catch (Exception e) {
			System.out.println("logout, updateMemberError: " + e.getMessage());
			resp.failure(ErrorCode.ERROR_DATABASE);
		}
		return resp;
	}

	@Override
	public List<WiAdmin> queryAdminByCompanyId(Integer adminId, Integer companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response insertAdmin(AdminInsert param) {
		Response resp = new Response();

		//操作管理员是否合法
		WiAdminExample example = new WiAdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(param.getAdminId());
		List<WiAdmin> adminList = adminDao.selectByExample(example);
		if (adminList.size() == 0) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}

		WiAdmin admin = adminList.get(0);
		//除了Administrator用户检测 新增用户公司是否合法 
		if ( !admin.getAccount().equals(Constants.FISE_SUPPER_ACCOUNT_NAME) && !param.getOrganizationId().equals(admin.getCompanyId())){
			resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
			return resp;
		}

		//新增人员权限必须小于等于自己最大权限
		if( param.getRuleId() != null && param.getRuleId().compareTo(admin.getAuthLevel()) < 0 ){
			resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
			return resp;
		}
		//查看新增用户是否合法
		example.clear();
		Criteria con2 = example.createCriteria();
		con2.andAccountEqualTo(param.getAccount());
		if ( adminDao.countByExample(example) > 0l) {
			resp.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_EXIST);
			return resp;
		}
		
		WiAdmin record = new WiAdmin();
		record.setAccount(param.getAccount());
		record.setPassword(param.getPassword());
		record.setCompanyId(param.getOrganizationId());
		Integer nNow = DateUtil.getLinuxTimeStamp();

		record.setCreated(nNow);
		record.setUpdated(nNow);
		record.setNickName(StringUtil.isEmpty(param.getNickName()) ? "" : param.getNickName());
		record.setAuthLevel((param.getRuleId() == null ? 0 : param.getRuleId()));
		record.setEmail(StringUtil.isEmpty(param.getEmail()) ? "" : param.getEmail());
		record.setPhone(StringUtil.isEmpty(param.getPhone()) ? "" : param.getPhone());
		record.setSalt(nNow.toString().substring(5, 9));
		record.setStatus(1);
		record.setAccessToken("");
		record.setLastLogin(0);
		adminDao.insert(record);
		resp.success(record);
		return resp;
	}

	@Override
	public Response updateAdmin(AdminUpdate param) {
		Response resp = new Response();
		//查询目标管理员是否有效
		WiAdmin updAdmin = new WiAdmin();
		WiAdminExample example = new WiAdminExample();
		Criteria updWhere = example.createCriteria();
		updWhere.andIdEqualTo(param.getAdminId());
		List<WiAdmin> adminList = adminDao.selectByExample(example);
		if (adminList.size() == 0) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		updAdmin = adminList.get(0);
		
		//检测发起请求的用户是否有权限更改目标用户信息
		WiAdmin loginAdmin = new WiAdmin();
		example.clear();
		Criteria loginWhere = example.createCriteria();
		loginWhere.andIdEqualTo(param.getAdminId());
		adminList.clear();
		adminList = adminDao.selectByExample(example);
		if (adminList.size() == 0) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		loginAdmin = adminList.get(0);
		if(!loginAdmin.getCompanyId().equals(updAdmin.getCompanyId()) && !loginAdmin.getAccount().equals(Constants.FISE_SUPPER_ACCOUNT_NAME)){
			resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
			return resp;
		}
		
		WiAdmin sqlAdmin = new WiAdmin();
		sqlAdmin.setId(param.getAdminId());
		sqlAdmin.setUpdated(DateUtil.getLinuxTimeStamp());
		if (!StringUtil.isEmpty(param.getAccount())){
			sqlAdmin.setAccount(param.getAccount());
		}

		if (!StringUtil.isEmpty(param.getEmail())){
			sqlAdmin.setEmail(param.getEmail());
		}

		if (!StringUtil.isEmpty(param.getNickName())){
			sqlAdmin.setNickName(param.getNickName());
		}

		if (!StringUtil.isEmpty(param.getEmail())){
			sqlAdmin.setEmail(param.getEmail());
		}

		if (param.getRuleId() != null){
			if(param.getAdminId().equals(param.getLoginId()) || loginAdmin.getAuthLevel().compareTo(param.getRuleId()) > 0){
				//不允许自己更改自己的角色 + 不允许更新比自己更高等级权限
				resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
				return resp;
			}
			sqlAdmin.setAuthLevel(param.getRuleId());
		}
		if (!StringUtil.isEmpty(param.getPhone())){
			sqlAdmin.setPhone(param.getNickName());
		}

		if(param.getStatus() != null){
			sqlAdmin.setStatus(param.getStatus());
		}
		adminDao.updateByPrimaryKeySelective(sqlAdmin);
		resp.success();
		return resp;
	}

}
