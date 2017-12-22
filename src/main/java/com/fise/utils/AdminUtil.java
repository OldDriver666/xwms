package com.fise.utils;

import com.fise.framework.redis.RedisManager;

import redis.clients.jedis.Jedis;

public class AdminUtil {

    public static Integer getRoleId(Integer memberId){
        Jedis jedis=null;
        String role_id="";
        try {
            jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
            String key = Constants.REDIS_KEY_PREFIX_MEMBER_ROLE_ID + "_" + memberId;
            role_id = jedis.get(key);
            role_id=role_id.substring(0, role_id.length()-1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
        }
        return Integer.valueOf(role_id);
    }
}
