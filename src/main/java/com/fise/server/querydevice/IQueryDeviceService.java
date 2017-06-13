package com.fise.server.querydevice;

import com.fise.base.Response;
import com.fise.model.param.QueryDeviceParam;


public interface IQueryDeviceService {
    //查询fisedevice配置
    public Response queryDeviceByAccount(QueryDeviceParam param);
    
}
