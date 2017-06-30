package com.fise.controller.report;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.model.param.ReportActivateParam;
import com.fise.server.report.IReportService;

@RestController
@RequestMapping("/boss/report")
public class ReportController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource IReportService reportSvr;
    
    /*用户设备激活日趋势*/
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public Response queryActivate(@RequestBody ReportActivateParam param){
        Response resp = new Response();
        logger.debug(param.toString());
        if( param.getBeginDate().compareTo(param.getEndDate()) > 0 ){
            //开始日期大于结束日期
            resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
            resp.setMsg("开始日期不能大于结束日期");
        } else {
            resp = reportSvr.queryActivate(param);
        }
        return resp;
    }

    /*统计页面概述*/
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public Response queryDashboard(@RequestBody Map<String,Integer> param){
        Response resp = new Response();
        if( param.get("organ_id") == null ){
            resp.failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
            resp.setMsg("公司不能为空");
        } else {
            resp = reportSvr.queryAboutPage(param.get("organ_id"));
        }
        return resp;
    }
    
}
