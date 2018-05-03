package com.fise.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fise.dao.IMClientTypeMapper;
import com.fise.dao.ReportMapper;
import com.fise.model.entity.IMClientType;
import com.fise.model.entity.IMClientTypeExample;
import com.fise.server.event.IEventService;
import com.fise.server.report.IReportService;
import com.fise.utils.EmailUtil;
import com.fise.utils.enums.EventTypeEnums;

@Component 
public class ReportTask {
	
    @Autowired
    ReportMapper reportDao;
    
    @Resource IReportService reportSvr;
    
	@Autowired
	IMClientTypeMapper imClientTypeDao;
	
	@Resource
	IEventService eventSvr;
   
    /**
     * 定时任务，每天凌晨0点发送日报邮件
     * @throws Exception 
     */
//	@Scheduled(fixedRate = 1000*30)
//	@Scheduled(cron= "0 0 0 * * ?")
    public void show(){
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//		Date curDate = DateUtil.getYesterday();
//		String date = df.format(curDate);
		String date = "2018-01-23";
		String title = date + "小位管理系统日报";
		StringBuffer content = new StringBuffer();
		
		//用户激活/注册数量
		int acount = reportDao.activeCount(date);
		content.append("用户激活/注册数量:").append(acount).append("<br/>");
		
		//用户在线人数
		int ocount = reportDao.onlinePeopleCount(date);
		content.append("在线用户数:").append(ocount).append("<br/>");
		
		//在线设备数量
		int dcount = reportDao.onlineDeviceCount(date);
		content.append("在线设备数:").append(dcount).append("<br/>");
		content.append("<br/>");
		
		//设备消息数
		int mcount = 0;
		Map<Integer,Integer> msgMap = reportSvr.queryTypeDayMessages(date);
		for (Entry<Integer, Integer> entry : msgMap.entrySet()) {
			mcount += entry.getValue();
		}
		content.append("消息总数:").append(mcount).append("<br/>");
		IMClientTypeExample example=new IMClientTypeExample();
		List<IMClientType> fisedevicelist=imClientTypeDao.selectByExample(example);
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		for (IMClientType imClientType : fisedevicelist) {
			typeMap.put(imClientType.getClienttype(), imClientType.getClientname());
		}
		for (Entry<Integer, Integer> entry : msgMap.entrySet()) {
			content.append(typeMap.get(entry.getKey())).append("消息数:").append(entry.getValue()).append("<br/>");
		}
		content.append("<br/>");
		//事件总数
		int ecount = 0;
		Map<Integer, Integer> eventMap = eventSvr.queryTypeDayEvents(date);
		for (Entry<Integer, Integer> entry : eventMap.entrySet()) {
			ecount += entry.getValue();
		}
		content.append("事件总数:").append(ecount).append("<br/>");

		for (Entry<Integer, Integer> entry : eventMap.entrySet()) {
			content.append(entry.getKey()).append(EventTypeEnums.getNameByNo(entry.getKey())).append("事件数:").append(entry.getValue()).append("<br/>");
		}
		
		
    	try {
//			EmailUtil.sendEmail(title, content.toString());
			System.out.println(content.toString());
		} catch (Exception e) {
			System.out.println("----发送小位管理系统日报邮件失败");
		}
        
    }
}
