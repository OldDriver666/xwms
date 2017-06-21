package com.fise.server.queryuser;

import com.fise.base.Response;
import com.fise.model.param.QueryUserParam;

public interface IQueryUserService {
    //查询User
    public Response queryUser(QueryUserParam param);
}
