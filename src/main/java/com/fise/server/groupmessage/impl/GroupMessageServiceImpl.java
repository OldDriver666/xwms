package com.fise.server.groupmessage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.IMGroupMapper;
import com.fise.dao.IMGroupMessage0Mapper;
import com.fise.model.entity.IMGroup;
import com.fise.model.entity.IMGroupExample;
import com.fise.model.entity.IMGroupExample.Criteria;
import com.fise.model.entity.IMGroupMessage0;
import com.fise.model.entity.IMGroupMessage0Example;
import com.fise.model.param.GroupMessageQueryParam;
import com.fise.server.groupmessage.IGroupMessageService;
import com.fise.utils.Constants;

@Service 
public class GroupMessageServiceImpl implements IGroupMessageService{
    
    @Autowired
    IMGroupMapper IMGroupDao;
    
    @Autowired
    IMGroupMessage0Mapper IMGroupMessage0Dao;
    
    @Override
    public Response query(Page<GroupMessageQueryParam> param) {
        
        Response response=new Response();
        
        //查询群ID
        IMGroupExample example=new IMGroupExample();
        Criteria criteria=example.createCriteria();
        criteria.andNameEqualTo(param.getParam().getName());
        List<IMGroup> list=IMGroupDao.selectByExample(example);
        if(list.size()==0){
            return response.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
        }
        Integer groupId=list.get(0).getId();
        String tableName="IMGroupMessage_"+groupId.intValue() % 8;
        
        IMGroupMessage0Example example2=new IMGroupMessage0Example();
        IMGroupMessage0Example.Criteria criteria2=example2.createCriteria();
        criteria2.andGroupidEqualTo(groupId);
        example2.setOrderByClause("created asc");
        List<IMGroupMessage0> list2=IMGroupMessage0Dao.selectByExample(tableName,example2,param);
        
        Page<IMGroupMessage0> page=new Page<IMGroupMessage0>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalCount(param.getTotalCount());
        page.setTotalPageCount(param.getTotalPageCount());
        page.setResult(list2);
        response.success(page);
        return response;
    }

}
