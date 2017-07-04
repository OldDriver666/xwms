package com.fise.server.report.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.KeyValueMap;
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
        Map<String, Integer> resultData = new HashMap<String, Integer>();
        for (ActivateResult tmp : data){
            resultData.put(tmp.getQueryDate(), tmp.getCount());
        }
        resp.success(resultData);
        return resp;
    }

    @Override
    public Response queryAboutPage(Integer companyId) {
        Response resp = new Response();
        
        List<KeyValueMap> sexCount = reportDao.queryUserSexCount(companyId);
        List<KeyValueMap> typeCount = reportDao.queryUserTypeCount(companyId);
        List<KeyValueMap> provinceCount = reportDao.queryUserProviceCount(companyId);
        
        Map<String, Object> resultData = new HashMap<String, Object>();
        
        Map<String, Integer> sexData = new HashMap<String, Integer>();
        for (KeyValueMap tmp : sexCount){
            sexData.put(tmp.getKeyName(), ((Long)tmp.getKeyValue()).intValue());
        }
        resultData.put("sex", sexData);

        Map<String, Integer> typeData = new HashMap<String, Integer>();
        for (KeyValueMap tmp : typeCount){
            typeData.put(tmp.getKeyName(), ((Long)tmp.getKeyValue()).intValue());
        }
        resultData.put("type", typeData);
        
        String splitStr = new String();
        int index = 0;
        Map<String, Integer> provinceData = new HashMap<String, Integer>();
        for (KeyValueMap tmp : provinceCount){
            //去掉"省"字
            splitStr = tmp.getKeyName();
            index = splitStr.indexOf("省");
            if (index == -1){
                index = splitStr.length();
            }
            provinceData.put(splitStr.substring(0, index), ((Long)tmp.getKeyValue()).intValue());
        }
        resultData.put("province", provinceData);
        resp.success(resultData);
        return resp;
    }

}
