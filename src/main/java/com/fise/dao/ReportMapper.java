package com.fise.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fise.model.param.ReportActivateParam;
import com.fise.model.result.ActivateResult;

public interface ReportMapper {
    
    List<ActivateResult> queryActivateCountByDate(@Param("param") ReportActivateParam param);
    
    List<Map<String,Integer>> queryUserSexCount(Integer companyId);
    
    List<Map<Integer,Integer>> queryUserTypeCount(Integer companyId);
    
    List<Map<String,Integer>> queryUserProviceCount(Integer companyId);
}