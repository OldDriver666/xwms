package com.fise.server.sessionmessage;

import com.fise.base.Response;
import com.fise.model.param.SessionMessageQueryParam;

public interface ISessionMessageService {
    //查询回话记录
    Response query(SessionMessageQueryParam param);
}
