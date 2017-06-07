package com.fise.server.serviceconf;

import com.fise.base.Response;
import com.fise.model.entity.IMServiceConf;
import com.fise.model.param.ServiceConfParam;

public interface IServiceConfService {
	/*添加serviceconf信息*/
	public Response insertServiceConf(IMServiceConf record);
	
	/*查询serviceconf信息*/
	public Response selectServiceConf(ServiceConfParam param);
	
	/*删除serviceconf信息*/
	public Response delServiceConf(ServiceConfParam param);
	
	/*修改serviceconf信息*/
	public Response updateServiceConf(IMServiceConf record);
}
