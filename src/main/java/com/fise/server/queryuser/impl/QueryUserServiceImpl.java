package com.fise.server.queryuser.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.IMUserMapper;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.param.QueryUserParam;
import com.fise.server.queryuser.IQueryUserService;
import com.fise.utils.StringUtil;

@Service
public class QueryUserServiceImpl implements IQueryUserService{
    
    @Autowired
    IMUserMapper IMUserDao;
    
    @Override
    public Response queryUser(QueryUserParam param) {
        
        Response response=new Response();
        
        IMUserExample example=new IMUserExample();
        Criteria criteria=example.createCriteria();
        
        if(!StringUtil.isEmpty(param.getDomain())){
            criteria.andDomainEqualTo(param.getDomain());
        }
        
        if(!StringUtil.isEmpty(param.getNick())){
            criteria.andNickEqualTo(param.getNick());
        }
        
        List<IMUser> list=IMUserDao.selectByExample(example);
        
        if(list.size()==0){
            return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
        }
        response.success(list);
        return response;
    }

}
