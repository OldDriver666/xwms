package com.fise.server.sessionmessage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.IMMessage0Mapper;
import com.fise.dao.IMRelationShipMapper;
import com.fise.model.entity.IMMessage0;
import com.fise.model.entity.IMMessage0Example;
import com.fise.model.entity.IMRelationShip;
import com.fise.model.entity.IMRelationShipExample;
import com.fise.model.entity.IMRelationShipExample.Criteria;
import com.fise.model.param.SessionMessageQueryParam;
import com.fise.server.sessionmessage.ISessionMessageService;

@Service
public class SessionMessageServiceImpl implements ISessionMessageService{
    
    @Autowired
    IMRelationShipMapper IMRelationShipDao;
    
    @Autowired
    IMMessage0Mapper IMMessageDao;
    
    @Override
    public Response query(SessionMessageQueryParam param) {
        
        Response response=new Response();
        
        IMRelationShipExample example=new IMRelationShipExample();
        Criteria criteria=example.createCriteria();
        criteria.andSmallidEqualTo(param.getSmallId());
        criteria.andBigidEqualTo(param.getBigId());
        List<IMRelationShip> list=IMRelationShipDao.selectByExample(example);
        
        if(list.size()==0){
            return response.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
        }
        
        Integer relateId=list.get(0).getId();
        String tableName="IMMessage_"+relateId % 8;
        
        IMMessage0Example example2=new IMMessage0Example();
        IMMessage0Example.Criteria criteria2=example2.createCriteria();
        criteria2.andRelateidEqualTo(relateId);
        
        example2.setLimit(100);
        example2.setOrderByClause("created desc");
        List<IMMessage0> list2=IMMessageDao.selectByExample(tableName, example2);
        
        response.success(list2);
        return response;
    }

}
