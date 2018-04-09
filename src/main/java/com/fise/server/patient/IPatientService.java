package com.fise.server.patient;

import com.fise.base.Response;
import com.fise.model.entity.WiPatient;


public interface IPatientService {
    /*查询精神病人信息*/
    Response queryPatient(WiPatient param);
    
    /*新增精神病人信息*/
    Response insertPatient(WiPatient param);
    
    /*删除精神病人信息*/
    Response delPatient(WiPatient param);
    
    /*修改精神病人信息*/
    Response updatePatient(WiPatient param);
}
