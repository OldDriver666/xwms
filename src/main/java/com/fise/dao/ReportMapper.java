package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.KeyValueMap;
import com.fise.model.param.ReportActivateParam;
import com.fise.model.result.ActivateResult;

public interface ReportMapper {
    
    List<ActivateResult> queryActivateCountByDate(@Param("param") ReportActivateParam param);
    
    List<KeyValueMap> queryUserSexCount(Integer companyId);
    
    List<KeyValueMap> queryUserTypeCount(Integer companyId);
    
    List<KeyValueMap> queryUserProviceCount(Integer companyId);
}