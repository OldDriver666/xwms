package com.fise.framework.aspect;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.fise.base.HttpContext;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.framework.exception.AuthException;
import com.fise.framework.exception.RequestHeaderException;
import com.fise.framework.redis.RedisManager;
import com.fise.server.auth.IAuthService;
import com.fise.utils.Constants;
import com.fise.utils.StringUtil;

import redis.clients.jedis.Jedis;
/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-24
 * @desc 检查access token的切面
 */
public class AuthAspect {
	
    @Resource
    IAuthService authService;
    
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        Logger logger = Logger.getLogger("AuthAspect");
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        
        // 若目标方法忽略了安全性检查，则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreAuth.class)) {
            return pjp.proceed();
        }
        // 从 request header 中获取当前 token
        HttpServletRequest req = HttpContext.getRequest();
        String uri = req.getRequestURI();
        StringBuffer url = req.getRequestURL();
        String domain = url.substring(0, url.toString().length() - uri.length() + 1);
        
        String accessToken = null;
        String redisPoolName = null;
        String keyPrefix = null;
        String fitUA = req.getHeader(Constants.HEADER_FIELD_FIT_USER_AGENT);
        if (StringUtil.isEmpty(fitUA)) {
            throw new RequestHeaderException("FISE-UA is empty!");
        }
        
        String[] uaField = fitUA.split("\\|");
        if (uaField.length < 5) {
            throw new RequestHeaderException("FISE-UA format error!");
        }
        
        String platform = uaField[0];
        String system = uaField[1];
        String udid = uaField[2];
        String id = uaField[3];
        //特别启用该字段为菜单module_id
        String versionName = uaField[4];
        
        logger.debug("UA=" + fitUA + " URI=" + uri);
        if (StringUtil.isEmpty(id)) {
            throw new RequestHeaderException("user id is empty!");
        }
        if (StringUtil.isEmpty(versionName)) {
            throw new RequestHeaderException("version name is empty!");
        }
        HttpContext.setVersionName(versionName);
        HttpContext.setPlatform(platform);
        if (uri.startsWith("/boss") || uri.startsWith("/managesvr")) {
            HttpContext.setMemberId(Integer.parseInt(id));
            accessToken = req.getHeader(Constants.HEADER_FIELD_NAME_ACCESS_TOKEN);
            redisPoolName = Constants.REDIS_POOL_NAME_MEMBER;
            keyPrefix = Constants.REDIS_KEY_PREFIX_MEMBER_ACCESS_TOKEN;
        } else if (uri.startsWith("/manage")) {
            HttpContext.setManagerId(Integer.parseInt(id));
            accessToken = req.getHeader(Constants.MANAGER_HEADER_FIELD_NAME_ACCESS_TOKEN);
            redisPoolName = Constants.REDIS_POOL_NAME_SYSTEM;
            keyPrefix = Constants.REDIS_KEY_PREFIX_MANAGER_ACCESS_TOKEN;
        } else if (uri.startsWith("/gym")) {
            HttpContext.setGymId(Integer.parseInt(id));
            accessToken = req.getHeader(Constants.GYM_HEADER_FIELD_NAME_ACCESS_TOKEN);
            redisPoolName = Constants.REDIS_POOL_NAME_GYM;
            keyPrefix = Constants.REDIS_KEY_PREFIX_GYM_ACCESS_TOKEN;
        } else {
            String message = "uri=" + uri + " not allowed";
            throw new Exception(message);
        }
        
        if (StringUtil.isEmpty(accessToken)) {
            throw new AuthException("access token is empty!");
        }
        logger.debug("id=" + id + "|redis_pool_name="+ redisPoolName + "|key_prefix=" + keyPrefix + "|token=" + accessToken + "|fit-ua=" + fitUA);
        Jedis jedis = null;
        try {
            jedis = RedisManager.getInstance().getResource(redisPoolName);
            String key = keyPrefix + accessToken;
            String idInRedis = jedis.get(key);
            if (StringUtil.isEmpty(idInRedis) || !idInRedis.equals(id)) {
                throw new AuthException("Auth failed!");
            }
        } finally {
            RedisManager.getInstance().returnResource(redisPoolName, jedis);
        }
        
        //设置companyId
        redisPoolName = Constants.REDIS_POOL_NAME_MEMBER;
        try {
            jedis = RedisManager.getInstance().getResource(redisPoolName);
            String key = Constants.REDIS_KEY_PREFIX_MEMBER_COMPANY_ID + "_" + id;
            String companyId = jedis.get(key);
            if (StringUtils.isNotBlank(companyId)) {
            	HttpContext.setCompanyId(Integer.valueOf(companyId));
			}
        } finally {
            RedisManager.getInstance().returnResource(redisPoolName, jedis);
        }
        Boolean flag = false;
        //校验增删改查权限
//        if (StringUtils.contains(uri, "Query") || StringUtils.contains(uri, "query") 
//        		|| StringUtils.contains(uri, "Select") || StringUtils.contains(uri, "select")) {
//			flag =!authService.queryAuth();
//		}
        
        if (StringUtils.contains(uri, "Insert") || StringUtils.contains(uri, "insert") 
        		|| StringUtils.contains(uri, "Add") || StringUtils.contains(uri, "add")) {
        	flag =!authService.inserAuth();
        }
        
        if (StringUtils.contains(uri, "Update") || StringUtils.contains(uri, "update") 
        		|| StringUtils.contains(uri, "Modify") || StringUtils.contains(uri, "modify")) {
        	flag =!authService.updateAuth();
		}
        
        if (StringUtils.contains(uri, "Delete") || StringUtils.contains(uri, "delete") 
        		|| StringUtils.contains(uri, "Del") || StringUtils.contains(uri, "del")) {
        	flag =!authService.updateAuth();
		}
        if (flag) {
        	throw new AuthException("Auth failed!");
		}
       
        logger.debug("domain=" + domain + "|uri=" + uri + "|token: " + accessToken + "|method_name = " + method.getName() + "|url=" +url.toString());
        
        // 调用目标方法
        return pjp.proceed();
    }
}