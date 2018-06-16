package com.fise.server.systemconf;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.entity.IMSystemConf;
import com.fise.model.param.SystemConfParam;

public interface ISystemConfService {
	/*添加systemconf信息*/
	public Response insertSystemConf(IMSystemConf record);
	
	/*查询系统配置信息*/
	public Response querySystemConf(SystemConfParam param);
	
	/*删除系统配置信息*/
	public Response delSystemConf(SystemConfParam param);
	
	/*修改系统配置信息*/
	public Response updateSystemConf(IMSystemConf record);
	
    /* 分页查询系统配置信息*/
    public Response queryIMSystemConfByPage(Page<IMSystemConf> page);
}
