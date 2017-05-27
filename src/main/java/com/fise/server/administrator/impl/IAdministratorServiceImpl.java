package com.fise.server.administrator.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.TBAdminstratorMapper;
import com.fise.framework.redis.RedisManager;
import com.fise.model.entity.TBAdminstrator;
import com.fise.model.entity.TBAdminstratorExample;
import com.fise.model.entity.TBAdminstratorExample.Criteria;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.server.administrator.IAdministratorService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;

import redis.clients.jedis.Jedis;


@Service
public class IAdministratorServiceImpl implements IAdministratorService {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private TBAdminstratorMapper adminDao;
	
	
	@Override
	public Response login(LoginParam param) {
		
		Response resp = new Response();

		TBAdminstratorExample example = new TBAdminstratorExample();
		Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(param.getAccount());
		List<TBAdminstrator> adminList = adminDao.selectByExample(example);
		if (adminList.size() == 0) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		TBAdminstrator admin = adminList.get(0);
		if (!param.getPassword().equals(admin.getPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		
		resp = login(admin);
		return resp;

	}

	private Response login(TBAdminstrator admin)
	{
		Response resp = new Response();
		
		Integer adminId = admin.getId();
		String accessToken = genAccessToken(adminId);
		TBAdminstrator updateAdmin = new TBAdminstrator();
		long nowTime = System.currentTimeMillis();
		updateAdmin.setAccessToken(accessToken);
		updateAdmin.setId(adminId);
		updateAdmin.setLastLogin((int) nowTime);
		
		adminDao.updateByPrimaryKeySelective(updateAdmin);
		admin.setAccessToken(accessToken);
		admin.setLastLogin((int) nowTime);
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
		String accessToken = request.getHeader(Constants.MANAGER_HEADER_FIELD_NAME_ACCESS_TOKEN);
		try {
			
			delAccessToken(memberId, accessToken);
			resp.success();
			
		} catch (Exception e) {
			System.out.println("logout, updateMemberError: " + e.getMessage());
			resp.failure(ErrorCode.ERROR_DATABASE);
		}
		return resp;
	}

	@Override
	public List<TBAdminstrator> queryAdminByCompanyId(Integer adminId, Integer companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
