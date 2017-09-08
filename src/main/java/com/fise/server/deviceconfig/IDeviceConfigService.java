package com.fise.server.deviceconfig;

import com.fise.base.Response;
import com.fise.model.param.QueryDeviceParam;


public interface IDeviceConfigService {
    //查询fisedevice配置
    public Response queryDeviceByAccount(QueryDeviceParam param);
    
}
