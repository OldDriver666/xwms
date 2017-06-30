package com.fise.server.report.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.Response;
import com.fise.dao.ReportMapper;
import com.fise.model.param.ReportActivateParam;
import com.fise.model.result.ActivateResult;
import com.fise.server.report.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    ReportMapper reportDao;
    
    @Override
    public Response queryActivate(ReportActivateParam param) {
        Response resp = new Response();
        List<ActivateResult> data = reportDao.queryActivateCountByDate(param);
        resp.success(data);
        return resp;
    }

    @Override
    public Response queryAboutPage(Integer companyId) {
        Response resp = new Response();
        
        List<Map<String, Integer>> sexCount = reportDao.queryUserSexCount(companyId);
        List<Map<Integer, Integer>> typeCount = reportDao.queryUserTypeCount(companyId);
        List<Map<String, Integer>> provinceCount = reportDao.queryUserProviceCount(companyId);
        
        Map<String, Object>  result = new HashMap<String, Object>();
        result.put("sex", sexCount);
        result.put("type", typeCount);
        result.put("province", provinceCount);

        resp.success(result);
        
        return resp;
    }




}
