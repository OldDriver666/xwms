package com.fise.server.report;

import java.util.List;
import java.util.Map;

import com.fise.base.Response;
import com.fise.model.param.ReportActivateParam;
import com.fise.model.result.MessageTypeResult;

public interface IReportService {
    /*查询用户设备日注册/激活数量*/
    public Response queryActivate(ReportActivateParam param);

    /*查询概述统计结果*/
    public Response queryAboutPage(Integer companyId);
    
    /*日消息总数*/
    public Response queryMessages(String daytime);
    
    /*设备日消息数*/
    public Map<Integer,Integer> queryTypeDayMessages(String daytime);
    
    /*消息类型分布*/
    public Response queryMessageType();
}
