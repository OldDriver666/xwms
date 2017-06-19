package com.fise.server.groupmessage;

import com.fise.base.Response;
import com.fise.model.param.GroupMessageQueryParam;

public interface IGroupMessageService {
    //查询群消息
    public Response query(GroupMessageQueryParam param);
}
