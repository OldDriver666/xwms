package com.fise.server.depart.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.DBFunctionMapper;
import com.fise.dao.WiAdminMapper;
import com.fise.dao.WiDepartmentMapper;
import com.fise.dao.WiOrganizationRoleMapper;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiAdminExample;
import com.fise.model.entity.WiDepartment;
import com.fise.model.entity.WiDepartmentExample;
import com.fise.model.entity.WiDepartmentExample.Criteria;
import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.param.DepartmentParam;
import com.fise.server.depart.IDepartmentService;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;


@Service
public class DepartmentServiceImpl implements IDepartmentService{
	
	@Autowired
	WiDepartmentMapper departDao;

	@Autowired
    DBFunctionMapper dbDao;
	
    @Autowired
    private WiAdminMapper adminDao;
    
    @Autowired
    WiOrganizationRoleMapper roleDao;
	
    @Override
    public Response insertOne(DepartmentParam record) {
        Response resp = new Response();
        //检查部门名字是否重复
        WiDepartmentExample example = new WiDepartmentExample();
        Criteria criteria =  example.createCriteria();
        criteria.andDepartNameEqualTo(record.getDepartName());
        criteria.andCompanyIdEqualTo(record.getCompanyId());
        List<WiDepartment> list = departDao.selectByExample(example);
        if (list.size() > 0) {
        	return resp.failure(ErrorCode.ERROR_DEPART_CONF_ACCOUNT_EXISTED);
		}
        
        WiDepartment data = new WiDepartment();
        data.setCompanyId(record.getCompanyId());
        data.setDepartName(record.getDepartName());
        data.setParentId(record.getParentId());
        data.setCreatorId(record.getCreatorId());
        data.setStatus(1);
        data.setUpdated(DateUtil.getLinuxTimeStamp());
        departDao.insertSelective(data);
        resp.success(record);
        return resp;
    }

    @Override
    public Response queryList(DepartmentParam param) {
        Response resp = new Response();
        
        WiAdmin admin = adminDao.selectByPrimaryKey(HttpContext.getMemberId());
//        WiOrganizationRole role = roleDao.selectByPrimaryKey(admin.getRoleId());
        WiDepartmentExample example = new WiDepartmentExample();
        Criteria con =  example.createCriteria();
        con.andCompanyIdEqualTo(admin.getCompanyId());
        if(admin.getDepartId() != null && admin.getDepartId() != 0){
            con.andIdIn(getChildDepatId(admin.getDepartId()));
        }
        if(StringUtil.isNotEmpty(param.getDepartName())){
        	con.andDepartNameLike(param.getDepartName());
        }
        con.andStatusEqualTo(1);
        List<WiDepartment> data = departDao.selectByExample(example);
        resp.success(data);
        return resp;
    }

    @Override
    public Response update(DepartmentParam param) {
        Response resp = new Response();
        WiDepartment record = new WiDepartment();
        record.setId(param.getDepartId());
        if(param.getStatus() != null){
            record.setStatus(param.getStatus());
        }
        if(!StringUtil.isEmpty(param.getDepartName())){
            record.setDepartName(param.getDepartName());
        }
        if(param.getParentId() != null){
            record.setParentId(param.getParentId());
        }
        record.setUpdated(DateUtil.getLinuxTimeStamp());
        record.setCreatorId(param.getCreatorId());
        departDao.updateByPrimaryKeySelective(record);
        resp.success(record);
        return resp;
    }
    
    
    @Override
    public Response delete(DepartmentParam param) {
    	Response resp = new Response();
    	departDao.deleteById(param);
    	resp.success(param);
    	return resp;
    }

    @Override
    public List<Integer> getChildDepatId(Integer parentId) {
        String childId = dbDao.getChildDepateId(parentId);
        String[] childList = childId.split(",");
        List<Integer> idList = new ArrayList<Integer>();
        for(int i=1;i < childList.length; i++){
            System.out.println(childList[i]);
            idList.add(Integer.valueOf(childList[i]));
        }
        return idList;
    }
    
    
	@Override
	public Response queryDepartmentByPage(Page<WiDepartment> page) {
		
		Response response=new Response();
		
		WiDepartmentExample example=new WiDepartmentExample();
		WiDepartmentExample.Criteria criteria=example.createCriteria();
		WiDepartment param = page.getParam();
        if(null != param.getCreatorId()){
        	criteria.andCreatorIdEqualTo(param.getCreatorId());
        }
        if(StringUtil.isNotEmpty(param.getDepartName())){
        	criteria.andDepartNameLike("%" + param.getDepartName() + "%");
        }
        if(StringUtil.isNotEmpty(param.getParentName())){
        	criteria.andParentNameLike("%" + param.getParentName() + "%");
        }

        page.setResult(departDao.selectByExampleByPage(example, page));
		return response.success(page);
	}

}
