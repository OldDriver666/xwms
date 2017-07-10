package com.fise.server.electricfence;

import com.fise.base.Response;
import com.fise.model.entity.ElectricFence;
import com.fise.model.param.ElectricFenceParam;

public interface IElectricFenceService {
    /*添加电子围栏*/
    Response addElectricFence(ElectricFence record);
    
    /*查询电子围栏*/
    Response queryElectricFence(ElectricFenceParam param);
    
    /*修改电子围栏*/
    Response updateElectricFence(ElectricFence record);
    
    /*删除电子围栏*/
    Response delElectricFence(ElectricFenceParam param);
}
