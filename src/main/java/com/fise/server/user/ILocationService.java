package com.fise.server.user;

import com.fise.base.Response;
import com.fise.model.param.QueryUserParam;

public interface ILocationService {

    public Response queryUserHistory(QueryUserParam param);

}
