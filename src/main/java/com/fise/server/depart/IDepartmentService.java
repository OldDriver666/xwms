package com.fise.server.depart;

import java.util.List;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.entity.WiAdmin;
import com.fise.model.entity.WiDepartment;
import com.fise.model.param.DepartmentParam;

public interface IDepartmentService {

	public Response insertOne(DepartmentParam record);
	
	public Response queryList(DepartmentParam param);
	
	public Response update(DepartmentParam record);
	
	public Response delete(DepartmentParam record);
	
	List<Integer> getChildDepatId(Integer parentId);
	
    /* 分页查询部门 */
    public Response queryDepartmentByPage(Page<WiDepartment> page);
}
