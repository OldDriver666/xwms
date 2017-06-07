package com.fise.server.device;

import com.fise.base.Response;
import com.fise.model.param.EventQueryParam;

public interface IEventService {

    Response query(EventQueryParam param);

}
