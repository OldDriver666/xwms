package com.fise.server.event;

import java.util.Map;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.EventQueryParam;

public interface IEventService {

    Response query(Page<EventQueryParam> param);
    
    /*设备事件数统计*/
    public Map<Integer,Integer> queryTypeDayEvents(String daytime);

}
