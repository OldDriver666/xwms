package com.fise.server.messagetype;

import com.fise.base.Response;

public interface IMessageTypeService {
    /*消息类型查询*/
    public Response queryMessageType(Integer type);
}
