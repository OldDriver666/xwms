package com.fise.server.organization.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.WiOrganizationMapper;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiAdminExample;
import com.fise.model.entity.WiOrganization;
import com.fise.model.entity.WiOrganizationExample;
import com.fise.model.entity.WiOrganizationExample.Criteria;
import com.fise.server.organization.IOrganizationService;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private WiOrganizationMapper organDao;
    
    @Override
    public Response QueryOrganization(String name) {
        Response resp =new Response();
        WiOrganizationExample example = new WiOrganizationExample();
        Criteria criteria=example.createCriteria();
        
        if(!StringUtil.isEmpty(name)){
            criteria.andNameLike(name);
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
        
        if(StringUtil.isEmpty(param.getName())){
            return resp.failure(ErrorCode.ERROR_FISE_DEVICE_PARAM_NULL);
        }
        
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
    
    @Override
	public Response queryOrganizationByPage(Page<WiOrganization> page) {
		
		Response response=new Response();
		
		WiOrganizationExample example=new WiOrganizationExample();
		WiOrganizationExample.Criteria criteria=example.createCriteria();
		WiOrganization param = page.getParam();
        
        if(StringUtil.isNotEmpty(param.getName())){
        	criteria.andNameLike("%" + param.getName() + "%");
        }
        criteria.andStatusNotEqualTo((byte) 2);

        page.setResult(organDao.selectByExampleByPage(example, page));
		return response.success(page);
	}

}
