package com.fise.server.organization.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.WiOrganizationMapper;
import com.fise.model.entity.WiOrganization;
import com.fise.model.entity.WiOrganizationExample;
import com.fise.model.entity.WiOrganizationExample.Criteria;
import com.fise.server.organization.IOrganizationService;
import com.fise.utils.DateUtil;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private WiOrganizationMapper organDao;
    
    @Override
    public Response QueryOrganization(String name) {
        Response resp =new Response();
        WiOrganizationExample example = new WiOrganizationExample();
        Criteria criteria=example.createCriteria();
        
        if(name!=null){
            criteria.andNameEqualTo(name);
        }
  
        List<WiOrganization> organList = organDao.selectByExample(example);
        resp.success(organList);
        return resp;
    }

    @Override
    public Response InsertOrganization(WiOrganization param) {
        Response resp =new Response();
        
        param.setCreated(DateUtil.getLinuxTimeStamp());
        param.setUpdated(DateUtil.getLinuxTimeStamp());
        organDao.insertSelective(param);
        return resp;
    }

    @Override
    public Response UpdateOrganization(WiOrganization param) {
        Response resp =new Response();
        param.setUpdated(DateUtil.getLinuxTimeStamp());
        organDao.updateByPrimaryKeySelective(param);
        return resp;
    }

    @Override
    public Response delOrganization(WiOrganization param) {
        
        Response response=new Response();
        
        organDao.deleteByPrimaryKey(param.getId());
        
        return response.success();
    }

}
