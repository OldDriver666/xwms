package com.fise.server.activedevicecount;

import com.fise.base.Response;

public interface IActiveDeviceCountService {
    //查询小位与设备的激活与在线统计
    public Response getActiveDeviceCount();
    
}
