package com.fise.server.user;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.QueryUserParam;

public interface IUserService {
    //查询User
    public Response queryUser(Page<QueryUserParam> param);
}
