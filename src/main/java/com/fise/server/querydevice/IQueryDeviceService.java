package com.fise.server.querydevice;

import com.fise.base.Response;
import com.fise.model.param.QueryDeviceByAccountParam;
import com.fise.model.param.QueryDeviceByMobileParam;
import com.fise.model.param.QueryDeviceByTypeParam;

public interface IQueryDeviceService {
    //查询fisedevice通过账号
    public Response queryDeviceByAccount(QueryDeviceByAccountParam param);
    
    //查询fisedevice通过号码
    public Response queryDeviceByMobile(QueryDeviceByMobileParam param);
    
    //查询fisedevice通过设备类型
    public Response queryDeviceByType(QueryDeviceByTypeParam param);
}
