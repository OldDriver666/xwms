package com.fise.server.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.WiAdminMapper;
import com.fise.dao.WiOrganizationRoleMapper;
import com.fise.dao.WiPermissionMapper;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiAdminExample;
import com.fise.model.entity.WiOrganizationRole;
import com.fise.model.entity.WiOrganizationRoleExample;
import com.fise.model.entity.WiOrganizationRoleExample.Criteria;
import com.fise.model.entity.WiPermission;
import com.fise.model.entity.WiPermissionExample;
import com.fise.model.param.InsertAuthParam;
import com.fise.model.param.InsertRoleParam;
import com.fise.model.param.QueryRoleParam;
import com.fise.model.param.RolePermissionParam;
import com.fise.model.result.ModulePermissResult;
import com.fise.server.depart.IDepartmentService;
import com.fise.server.module.IModuleService;
import com.fise.server.role.IRoleService;
import com.fise.utils.AdminUtil;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    WiOrganizationRoleMapper roleDao;

    @Autowired
    private WiPermissionMapper permissionDao;

    @Autowired
    private WiAdminMapper adminDao;
    
    @Resource
    IDepartmentService departSvr;

    @Resource
    IModuleService moduleSvr;

    @Override
    public List<WiOrganizationRole> queryRole(QueryRoleParam param) {

//        // 预先查询管理者权限
//        WiOrganizationRole admin = roleDao.selectByPrimaryKey(param.getRole_id());
//        if (admin == null) {
//            return null;
//        }
        WiOrganizationRoleExample example = new WiOrganizationRoleExample();
        WiOrganizationRoleExample.Criteria criterion = example.createCriteria();
//        criterion.andAuthLevelLessThan(admin.getAuthLevel());
        criterion.andOrganizationIdEqualTo(param.getCompany_id());
        criterion.andCreatorIdEqualTo(param.getCreator_id());
        return roleDao.selectByExample(example);
    }

    @Override
    public Response queryRoleAuth(QueryRoleParam param) {
//        // 需要返回的真实JSON格式数据
//        List<ModulePermissResult> data = new ArrayList<ModulePermissResult>();
//        List<ModulePermissResult> tmpData = new ArrayList<ModulePermissResult>();
//        List<ModulePermissResult> result = new ArrayList<ModulePermissResult>();
//        //先查询顶级目录权限 查出来的结果需要遍历一次
//        Integer needAll = 1;
//        if (param.getInclude_all() == null || param.getInclude_all() == 0)
//            needAll = 0;
//        data = permissionDao.selectAuthByRole(param.getCompany_id(), param.getRole_id(),0,needAll);
//        for (ModulePermissResult tmp : data) {
//            result.add(tmp);
//            tmpData.clear();
//            tmpData = permissionDao.selectAuthByRole(param.getCompany_id(), param.getRole_id(),tmp.getModule_id(),needAll);
//            result.addAll(tmpData);
//        }
    	Response response = new Response();
        // 判断用户权限
    	Integer roleId1 = param.getRole_id();
    	Integer roleId2 = AdminUtil.getRoleId(HttpContext.getMemberId());
    	if (roleId1 != roleId2) {
    		WiOrganizationRoleExample example = new WiOrganizationRoleExample();
        	WiOrganizationRoleExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(roleId1);
            criteria.andCreatorIdEqualTo(HttpContext.getMemberId());
            List<WiOrganizationRole> list = roleDao.selectByExample(example);
            if (list.isEmpty()) {
            	response.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
                response.setMsg("角色权限值错误");
                return response;
            }
		}

    	List<ModulePermissResult> data = permissionDao.selectAuthByRole(null, param.getRole_id(),null,null);
    	response.success(data);
    	return response;
    }

    @Override
    public Response updateRoleAuth(RolePermissionParam param) {

        Response resp = new Response();

        WiPermission dbValue = new WiPermission();
        dbValue.setId(param.getPermissionId());
        if (param.getInsertAuth() != null) {
            dbValue.setInsertAuth(param.getInsertAuth());
        }
        if (param.getQueryAuth() != null) {
            dbValue.setQueryAuth(param.getQueryAuth());
        }
        if (param.getUpdateAuth() != null) {
            dbValue.setUpdateAuth(param.getUpdateAuth());
        }
        if (param.getStatus() != null) {
            dbValue.setStatus(param.getStatus());
        }
        dbValue.setUpdated(DateUtil.getLinuxTimeStamp());
        permissionDao.updateByPrimaryKeySelective(dbValue);

        resp.success();
        return resp;
    }

    @Override
    public Response insertRole(InsertRoleParam role) {

        Response response = new Response();

        // 判断用户权限
        WiAdminExample example = new WiAdminExample();
        WiAdminExample.Criteria criteria1 = example.createCriteria();
        //根据创建的的ID去wi_admin表中查询是否存在该用户。
        criteria1.andIdEqualTo(role.getCreatorId());
        List<WiAdmin> list1 = adminDao.selectByExample(example);
        if (list1.isEmpty()) {
            response.failure(ErrorCode.ERROR_DATABASE);
            response.setMsg("用户不存在");
            return response;
        }

        Integer roleid = list1.get(0).getRoleId();
        
        WiOrganizationRoleExample example1 = new WiOrganizationRoleExample();
        Criteria criteria = example1.createCriteria();
        criteria.andIdEqualTo(roleid);
        List<WiOrganizationRole> list2 = roleDao.selectByExample(example1);
        if (list2.isEmpty() || list2.get(0).getAuthLevel() <= role.getRoleLevel()) {
            response.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
            response.setMsg("角色权限值错误");
            return response;
        }

        // 判断角色name是否已经存在
        WiOrganizationRoleExample example2 = new WiOrganizationRoleExample();
        Criteria criteri = example2.createCriteria();
        criteri.andNameEqualTo(role.getRoleName());
        criteri.andOrganizationIdEqualTo(role.getCompanyId());
        List<WiOrganizationRole> list = roleDao.selectByExample(example2);

        if (list.size() != 0) {
            response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_EXIST);
            response.setMsg("新增的角色已经存在！！！");
            return response;
        }

        WiOrganizationRole data = new WiOrganizationRole();
        data.setAuthLevel(role.getRoleLevel());
        data.setDescription(StringUtil.isEmpty(role.getDesc()) ? "" : role.getDesc());
        data.setDepartId(role.getDepartId() == null ? 0 : role.getDepartId());
        data.setName(role.getRoleName());
        data.setOrganizationId(role.getCompanyId());
        data.setCreatorId(role.getCreatorId());
        roleDao.insertSelective(data);

        return response.success();
    }

    @Override
    public Response delRole(WiOrganizationRole role) {
        Response resp = new Response();
        roleDao.deleteByPrimaryKey(role.getId());
        return resp.success();
    }
    
    @Override
    public Response delAuth(InsertAuthParam auth) {
        Response resp = new Response();
        roleDao.deleteByPrimaryKey(auth.getPermissionId());
        return resp.success();
    }

    @Override
    public Response updateRole(WiOrganizationRole param) {
        Response resp = new Response();
        roleDao.updateByPrimaryKeySelective(param);
        return resp.success();
    }

    @Override
    public Response insertAuth(InsertAuthParam param) {
    	Response resp = new Response();
    	//根据角色id和模块id查询是否有记录
    	WiPermissionExample example = new WiPermissionExample();
    	WiPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(param.getRoleId());
        criteria.andModuleIdEqualTo(param.getModuleId());
    	long l = permissionDao.countByExample(example);
    	//如果存在角色权限则修改
    	if (l > 0) {
    		WiPermission record = new WiPermission();
    		BeanUtils.copyProperties(param, record);
    		permissionDao.updateByRoleIdAndModleId(record);
		}else{
			WiPermission data = new WiPermission();
			BeanUtils.copyProperties(param, data);
			Integer tNow = DateUtil.getLinuxTimeStamp();
			data.setUpdated(tNow);
			data.setCreated(tNow);
			permissionDao.insert(data);
		}
        return resp.success();
    }

	@Override
	@Transactional
	public Response insertRoleAndAuths(InsertRoleParam role, List<InsertAuthParam> auths) {
		Response resp = new Response();
		resp = insertRole(role);
		if (!"ok".equals(resp.getMsg())){
			return resp;
		}
		WiOrganizationRoleExample example = new WiOrganizationRoleExample();
        Criteria criteri = example.createCriteria();
        criteri.andNameEqualTo(role.getRoleName());
        criteri.andOrganizationIdEqualTo(role.getCompanyId());
        List<WiOrganizationRole> list = roleDao.selectByExample(example);
        WiOrganizationRole wrole = list.get(0);
        if (!valiteAuths(auths)) {
        	resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
            resp.setMsg("角色权限值错误");
            return resp;
		}
        
		for (InsertAuthParam insertAuthParam : auths) {
			insertAuthParam.setCompanyId(role.getCompanyId());
			insertAuthParam.setRoleId(wrole.getId());
			resp = insertAuth(insertAuthParam);
		}
		
		return resp.success();
	}

	@Override
	@Transactional
	public Response updateRoleAndAuths(WiOrganizationRole role, List<InsertAuthParam> auths) {
		Response resp = new Response();
		
		if (!valiteAuths(auths)) {
        	resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
            resp.setMsg("角色权限值错误");
            return resp;
		}
		int idx = updateRoleById(role);
		
		if (idx > 0) {
			for (InsertAuthParam insertAuthParam : auths) {
				insertAuthParam.setRoleId(role.getId());
				insertAuthParam.setCompanyId(role.getOrganizationId());
				resp = insertAuth(insertAuthParam);
			}
		}
		return resp.success();
	}
	
	@Override
	@Transactional
	public Response deleteRoleAndAuths(InsertAuthParam role) {
		Response resp = new Response();
		
		int idx = roleDao.delRoleByRoleId(role);
	    if (idx > 0) {
	    	roleDao.delAuthByRoleId(role);
		}
		return resp.success();
	}
	
    private int updateRoleById(WiOrganizationRole param) {
        return roleDao.updateById(param);
    }
    
    /**
     * 验证权限是否是当前用户所拥有
     */
    private boolean valiteAuths(List<InsertAuthParam> auths) {
    	Integer roleId = AdminUtil.getRoleId(HttpContext.getMemberId());
    	List<ModulePermissResult> data = permissionDao.selectAuthByRole(null, roleId,null,null);
    	Boolean flag = false;
    	for (InsertAuthParam insertAuthParam : auths) {
    		flag = false;
    		for (ModulePermissResult modulePermissResult : data) {
    			if (insertAuthParam.getModuleId() == modulePermissResult.getModule_id()) {
					flag = true;
				}
    		}
    		if(!flag) {
    			return false;
    		}
		}
    	return true;
    }
    
    
    @Override
    public Response queryPatientAuth(QueryRoleParam param) {

    	Response response = new Response();
        // 判断用户权限
    	Integer roleId1 = param.getRole_id();
    	Integer roleId2 = AdminUtil.getRoleId(HttpContext.getMemberId());
    	if (roleId1 != roleId2) {
    		WiOrganizationRoleExample example = new WiOrganizationRoleExample();
        	WiOrganizationRoleExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(roleId1);
            criteria.andCreatorIdEqualTo(HttpContext.getMemberId());
            List<WiOrganizationRole> list = roleDao.selectByExample(example);
            if (list.isEmpty()) {
            	response.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
                response.setMsg("角色权限值错误");
                return response;
            }
		}

    	List<ModulePermissResult> data = permissionDao.queryPatientAuth(param.getRole_id());
    	response.success(data);
    	return response;
    }
    
    
    @Override
	public Response queryOrganizationRoleByPage(Page<WiOrganizationRole> page) {
		
		Response response=new Response();
		
		WiOrganizationRoleExample example=new WiOrganizationRoleExample();
		WiOrganizationRoleExample.Criteria criteria=example.createCriteria();
		WiOrganizationRole param = page.getParam();
        if(null != param.getCreatorId()){
        	criteria.andCreatorIdEqualTo(param.getCreatorId());
        }
        
        if(StringUtil.isNotEmpty(param.getName())){
        	criteria.andNameLike("%" + param.getName() + "%");
        }

        page.setResult(roleDao.selectByExampleByPage(example, page));
		return response.success(page);
	}
    
    @Override
    public List<ModulePermissResult> queryAuthByName(QueryRoleParam param) {
    	
    	return permissionDao.selectAuthByName(param.getCompany_id(), param.getRole_id(),param.getName());
    }

}
